package com.hiberus.show.mixer.listener;

import com.hiberus.show.library.topology.InputPlatformEvent;
import com.hiberus.show.library.topology.InputPlatformKey;
import com.hiberus.show.library.topology.InputShowEvent;
import com.hiberus.show.library.topology.InputShowKey;
import com.hiberus.show.library.topology.OutputShowPlatformListEvent;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;
import com.hiberus.show.mixer.binding.BinderProcessor;
import com.hiberus.show.mixer.service.ShowMixerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowMixerListener {

    private final ShowMixerService showMixerService;

    @StreamListener
    @SendTo(BinderProcessor.OUTPUT)
    public KStream<OutputShowPlatformListKey, OutputShowPlatformListEvent> process(
            @Input(BinderProcessor.SHOW) final KStream<InputShowKey, InputShowEvent> shows,
            @Input(BinderProcessor.PLATFORM) final KStream<InputPlatformKey, InputPlatformEvent> platforms) {
        return showMixerService.process(shows, platforms);
    }
}
