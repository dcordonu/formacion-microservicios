package com.hiberus.show.mixer.service;

import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.InputPlatformKey;
import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import com.hiberus.show.library.OutputShowPlatformListEvent;
import com.hiberus.show.library.OutputShowPlatformListKey;
import com.hiberus.show.library.PlatformListEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowMixerServiceImpl implements ShowMixerService {

    @Override
    public KStream<OutputShowPlatformListKey, OutputShowPlatformListEvent> process(
            final KStream<InputShowKey, InputShowEvent> shows,
            final KStream<InputPlatformKey, InputPlatformEvent> platforms) {

        final KTable<OutputShowPlatformListKey, InputShowEvent> showsTable = shows
                .peek((k, show) -> log.info("Show received: {}", show))
                .selectKey((inputShowKey, inputShowEvent) -> OutputShowPlatformListKey.newBuilder().setIsan(inputShowEvent.getIsan()).build())
                .groupByKey()
                .reduce((previous, current) -> current, Materialized.as("ktable-shows-reduce"));

        final KTable<OutputShowPlatformListKey, PlatformListEvent> platformsTable = platforms
                .peek((k, platform) -> log.info("Platform association received: {}", platform))
                .selectKey((inputPlatformKey, inputPlatformEvent) -> OutputShowPlatformListKey.newBuilder().setIsan(inputPlatformEvent.getIsan()).build())
                .groupByKey()
                .aggregate(this::initPlatformListEvent, (showKey, inputPlatformEvent, aggregate) -> {
                    if (!aggregate.getPlatforms().contains(inputPlatformEvent.getPlatform())) {
                        aggregate.getPlatforms().add(inputPlatformEvent.getPlatform());
                    }

                    return aggregate;
                }, Named.as("ktable-platforms"), Materialized.as("ktable-platforms-agg"));

        return showsTable.leftJoin(platformsTable, (inputShowEvent, platformListEvent) ->
                OutputShowPlatformListEvent.newBuilder()
                        .setName(inputShowEvent.getName())
                        .setPlatforms(platformListEvent != null ? platformListEvent.getPlatforms() : new ArrayList<>())
                        .build())
                .toStream()
                .peek((k, showPlatformListEvent) -> log.info("Sent message {} to output channel", showPlatformListEvent));
    }

    private PlatformListEvent initPlatformListEvent() {
        return PlatformListEvent.newBuilder().setPlatforms(new ArrayList<>()).build();
    }
}
