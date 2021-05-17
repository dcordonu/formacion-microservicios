package com.hiberus.show.mixer.service;

import com.hiberus.show.library.topology.EventType;
import com.hiberus.show.library.topology.InputPlatformEvent;
import com.hiberus.show.library.topology.InputPlatformKey;
import com.hiberus.show.library.topology.InputShowEvent;
import com.hiberus.show.library.topology.InputShowKey;
import com.hiberus.show.library.topology.OutputShowPlatformListEvent;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;
import com.hiberus.show.library.topology.PlatformListEvent;
import com.hiberus.show.mixer.topology.InputPlatformKeyMapper;
import com.hiberus.show.mixer.topology.InputShowEventValueMapper;
import com.hiberus.show.mixer.topology.InputShowKeyMapper;
import com.hiberus.show.mixer.topology.PlatformListAggregator;
import com.hiberus.show.mixer.topology.PlatformListFilter;
import com.hiberus.show.mixer.topology.ShowFilter;
import com.hiberus.show.mixer.topology.ShowPlatformListValueJoiner;
import com.hiberus.show.mixer.topology.ShowReducer;
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

    public static final String SHOW_TABLE = "ktable-shows-reduce";
    public static final String PLATFORM_TABLE = "ktable-platforms-agg";

    private final ShowReducer showReducer;
    private final InputShowKeyMapper inputShowKeyMapper;
    private final InputPlatformKeyMapper inputPlatformKeyMapper;
    private final PlatformListAggregator platformListAggregator;
    private final ShowPlatformListValueJoiner showPlatformListValueJoiner;
    private final ShowFilter showFilter;
    private final PlatformListFilter platformListFilter;
    private final InputShowEventValueMapper inputShowEventValueMapper;

    @Override
    public KStream<OutputShowPlatformListKey, OutputShowPlatformListEvent> process(
            final KStream<InputShowKey, InputShowEvent> shows,
            final KStream<InputPlatformKey, InputPlatformEvent> platforms) {

        final KStream<OutputShowPlatformListKey, InputShowEvent> keyMappedShowStream = shows
                .peek((k, show) -> log.info("Show received: {}", show))
                .selectKey(inputShowKeyMapper);

        final KStream<OutputShowPlatformListKey, InputShowEvent> keyMappedShowStreamToDelete = keyMappedShowStream
                .branch((k, v) -> EventType.DELETE.equals(v.getEventType()))[0];

        final KTable<OutputShowPlatformListKey, InputShowEvent> showsTable = keyMappedShowStream
                .filter(showFilter)
                .groupByKey()
                .reduce(showReducer, Named.as(SHOW_TABLE), Materialized.as(SHOW_TABLE));

        final KTable<OutputShowPlatformListKey, PlatformListEvent> platformsTable = platforms
                .peek((k, platform) -> log.info("Platform association received: {}", platform))
                .selectKey(inputPlatformKeyMapper)
                .filter(platformListFilter)
                .groupByKey()
                .aggregate(platformListAggregator, platformListAggregator, Named.as(PLATFORM_TABLE), Materialized.as(PLATFORM_TABLE));

        return showsTable.leftJoin(platformsTable, showPlatformListValueJoiner)
                .toStream()
                .merge(keyMappedShowStreamToDelete.mapValues(inputShowEventValueMapper))
                .filter((k, v) -> v != null)
                .peek((k, showPlatformListEvent) -> log.info("Sent message {} to output channel", showPlatformListEvent));
    }
}
