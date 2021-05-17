package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.topology.EventType;
import com.hiberus.show.library.topology.InputPlatformEvent;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;
import com.hiberus.show.library.topology.PlatformListEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PlatformListAggregator implements Aggregator<OutputShowPlatformListKey, InputPlatformEvent, PlatformListEvent>, Initializer<PlatformListEvent> {

    @Override
    public PlatformListEvent apply(final OutputShowPlatformListKey key, final InputPlatformEvent value, PlatformListEvent aggregate) {
        if (EventType.DELETE.equals(value.getEventType())) {
            aggregate.setPlatforms(aggregate.getPlatforms().stream().filter(p -> !p.equals(value.getPlatform())).collect(Collectors.toList()));
        } else {
            aggregate.getPlatforms().add(value.getPlatform());
        }

        log.info("Aggregate: {}", aggregate);

        if (aggregate.getPlatforms().isEmpty()) {
            log.info("List of platforms for isan {} is empty; sending tombstone...", value.getIsan());
            aggregate = null;
        }

        return aggregate;
    }

    @Override
    public PlatformListEvent apply() {
        return PlatformListEvent.newBuilder().setPlatforms(new ArrayList<>()).setEventType(EventType.CREATE).build();
    }
}
