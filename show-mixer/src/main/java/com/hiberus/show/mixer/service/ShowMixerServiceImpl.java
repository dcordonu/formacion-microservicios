package com.hiberus.show.mixer.service;

import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.InputPlatformKey;
import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import com.hiberus.show.library.PlatformListEvent;
import com.hiberus.show.library.ShowKey;
import com.hiberus.show.library.ShowPlatformEvent;
import com.hiberus.show.library.ShowPlatformListEvent;
import com.hiberus.show.mixer.lambda.PlatformListEventMapper;
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

    private final PlatformListEventMapper platformListEventMapper;

    @Override
    public KStream<ShowKey, ShowPlatformEvent> process(
            final KStream<InputShowKey, InputShowEvent> shows,
            final KStream<InputPlatformKey, InputPlatformEvent> platforms) {
        final KTable<ShowKey, InputShowEvent> showsTable = shows
                .peek((k, show) -> log.info("Show received: {}", show))
                .selectKey((inputShowKey, inputShowEvent) -> ShowKey.newBuilder().setIsan(inputShowEvent.getIsan()).build())
                .groupByKey()
                .reduce((previous, current) -> current, Materialized.as("ktable-shows"));

        final KTable<ShowKey, PlatformListEvent> platformsTable = platforms
                .peek((k, platform) -> log.info("Platform association received: {}", platform))
                .selectKey((inputPlatformKey, inputPlatformEvent) -> ShowKey.newBuilder().setIsan(inputPlatformEvent.getIsan()).build())
                .groupByKey()
                .aggregate(this::initPlatformListEvent, (showKey, inputPlatformEvent, aggregate) -> {
                    aggregate.getPlatforms().add(inputPlatformEvent.getPlatform());

                    // TODO preguntar y "poner como ejercicio" para evitar añadir la misma plataforma varias veces. Es
                    // TODO decir, evitar un ["HBO", "HBO", "Netflix"]

                    /*
                    if (!aggregate.getPlatforms().contains(inputPlatformEvent.getPlatform())) {
                        aggregate.getPlatforms().add(inputPlatformEvent.getPlatform());
                    }
                     */

                    return aggregate;
                }, Named.as("ktable-platforms"), Materialized.as("ktable-platforms-agg"));

        return showsTable.join(platformsTable, (inputShowEvent, platformListEvent) ->
                ShowPlatformListEvent.newBuilder()
                        .setName(inputShowEvent.getName()).setPlatforms(platformListEvent.getPlatforms()).build())
                .toStream()
                .peek((k, showPlatformListEvent) -> log.info("About to expand {} into {} messages", showPlatformListEvent, showPlatformListEvent.getPlatforms().size()))
                .flatMap(platformListEventMapper);
    }

    private PlatformListEvent initPlatformListEvent() {
        return PlatformListEvent.newBuilder().setPlatforms(new ArrayList<>()).build();
    }
}