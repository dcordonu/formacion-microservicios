package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.topology.InputShowEvent;
import com.hiberus.show.library.topology.OutputShowPlatformListEvent;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InputShowEventValueMapper implements ValueMapper<InputShowEvent, OutputShowPlatformListEvent> {

    @Override
    public OutputShowPlatformListEvent apply(InputShowEvent value) {
        return OutputShowPlatformListEvent.newBuilder()
                .setEventType(value.getEventType())
                .setName(value.getName())
                .setPlatforms(new ArrayList<>())
                .build();
    }
}
