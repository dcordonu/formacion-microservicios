package com.hiberus.show.mixer.service;

import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.InputPlatformKey;
import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import com.hiberus.show.library.ShowKey;
import com.hiberus.show.library.ShowPlatformEvent;
import org.apache.kafka.streams.kstream.KStream;

public interface ShowMixerService {

    KStream<ShowKey, ShowPlatformEvent> process(
            final KStream<InputShowKey, InputShowEvent> shows,
            final KStream<InputPlatformKey, InputPlatformEvent> platforms);
}