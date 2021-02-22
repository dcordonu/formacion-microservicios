package com.hiberus.show.mixer.lambda;

import com.hiberus.show.library.ShowKey;
import com.hiberus.show.library.ShowPlatformEvent;
import com.hiberus.show.library.ShowPlatformListEvent;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlatformListEventMapper implements KeyValueMapper<ShowKey, ShowPlatformListEvent, Iterable<? extends KeyValue<ShowKey, ShowPlatformEvent>>> {

    @Override
    public Iterable<? extends KeyValue<ShowKey, ShowPlatformEvent>> apply(final ShowKey key, final ShowPlatformListEvent value) {
        return value.getPlatforms().stream()
                .map(p -> KeyValue.pair(key, ShowPlatformEvent.newBuilder()
                        .setName(value.getName())
                        .setPlatform(p).build()))
                .collect(Collectors.toList());
    }
}
