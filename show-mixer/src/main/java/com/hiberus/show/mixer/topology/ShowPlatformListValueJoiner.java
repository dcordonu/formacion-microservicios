package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.OutputShowPlatformListEvent;
import com.hiberus.show.library.PlatformListEvent;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ShowPlatformListValueJoiner implements ValueJoiner<InputShowEvent, PlatformListEvent, OutputShowPlatformListEvent> {

    @Override
    public OutputShowPlatformListEvent apply(InputShowEvent inputShowEvent, PlatformListEvent platformListEvent) {
        return OutputShowPlatformListEvent.newBuilder()
                .setName(inputShowEvent.getName())
                .setEventType(inputShowEvent.getEventType())
                .setPlatforms(platformListEvent != null ? platformListEvent.getPlatforms() : new ArrayList<>())
                .build();
    }
}
