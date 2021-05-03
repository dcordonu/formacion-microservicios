package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import com.hiberus.show.library.OutputShowPlatformListKey;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.springframework.stereotype.Component;

@Component
public class InputShowKeyMapper implements KeyValueMapper<InputShowKey, InputShowEvent, OutputShowPlatformListKey> {

    @Override
    public OutputShowPlatformListKey apply(final InputShowKey key, final InputShowEvent value) {
        return OutputShowPlatformListKey.newBuilder().setIsan(value.getIsan()).build();
    }
}
