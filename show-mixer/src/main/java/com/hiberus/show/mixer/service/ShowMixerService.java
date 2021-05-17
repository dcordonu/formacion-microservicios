package com.hiberus.show.mixer.service;

import com.hiberus.show.library.topology.InputPlatformEvent;
import com.hiberus.show.library.topology.InputPlatformKey;
import com.hiberus.show.library.topology.InputShowEvent;
import com.hiberus.show.library.topology.InputShowKey;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;
import com.hiberus.show.library.topology.OutputShowPlatformListEvent;
import org.apache.kafka.streams.kstream.KStream;

public interface ShowMixerService {

    KStream<OutputShowPlatformListKey, OutputShowPlatformListEvent> process(
            final KStream<InputShowKey, InputShowEvent> shows,
            final KStream<InputPlatformKey, InputPlatformEvent> platforms);
}
