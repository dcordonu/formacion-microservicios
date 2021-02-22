package com.hiberus.show.mixer.binding;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface BinderProcessor {

    String OUTPUT = "output";
    String SHOW = "shows";
    String PLATFORM = "platforms";

    @Input(SHOW)
    KStream<?, ?> show();

    @Input(PLATFORM)
    KStream<?, ?> platform();

    @Output(OUTPUT)
    KStream<?, ?> output();
}
