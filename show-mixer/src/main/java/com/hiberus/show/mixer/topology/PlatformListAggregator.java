package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.OutputShowPlatformListKey;
import com.hiberus.show.library.PlatformListEvent;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PlatformListAggregator implements Aggregator<OutputShowPlatformListKey, InputPlatformEvent, PlatformListEvent>, Initializer<PlatformListEvent> {

    @Override
    public PlatformListEvent apply(final OutputShowPlatformListKey key, final InputPlatformEvent value, final PlatformListEvent aggregate) {
        aggregate.getPlatforms().add(value.getPlatform());

        return aggregate;
    }

    @Override
    public PlatformListEvent apply() {
        return PlatformListEvent.newBuilder().setPlatforms(new ArrayList<>()).build();
    }
}
