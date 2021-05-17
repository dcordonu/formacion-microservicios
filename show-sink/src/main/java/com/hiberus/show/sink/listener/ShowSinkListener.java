package com.hiberus.show.sink.listener;

import com.hiberus.show.library.topology.OutputShowPlatformListEvent;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;
import com.hiberus.show.sink.service.ShowSinkService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowSinkListener {

    private final ShowSinkService showSinkService;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void receive(final ConsumerRecord<OutputShowPlatformListKey, OutputShowPlatformListEvent> record) {
        this.showSinkService.performOperation(record.key(), record.value());
    }
}
