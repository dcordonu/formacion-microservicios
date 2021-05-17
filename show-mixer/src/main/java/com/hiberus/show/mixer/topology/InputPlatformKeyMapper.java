package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.topology.InputPlatformEvent;
import com.hiberus.show.library.topology.InputPlatformKey;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.springframework.stereotype.Component;

@Component
public class InputPlatformKeyMapper implements KeyValueMapper<InputPlatformKey, InputPlatformEvent, OutputShowPlatformListKey> {

    @Override
    public OutputShowPlatformListKey apply(final InputPlatformKey key, final InputPlatformEvent value) {
        return OutputShowPlatformListKey.newBuilder().setIsan(value.getIsan()).build();
    }
}
