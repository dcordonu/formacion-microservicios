package com.hiberus.show.mixer.listener;

import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.InputPlatformKey;
import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import com.hiberus.show.library.ShowKey;
import com.hiberus.show.library.ShowPlatformEvent;
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
    public KStream<ShowKey, ShowPlatformEvent> process(
            @Input(BinderProcessor.SHOW) final KStream<InputShowKey, InputShowEvent> shows,
            @Input(BinderProcessor.PLATFORM) final KStream<InputPlatformKey, InputPlatformEvent> platforms) {
        return showMixerService.process(shows, platforms);
    }
}
