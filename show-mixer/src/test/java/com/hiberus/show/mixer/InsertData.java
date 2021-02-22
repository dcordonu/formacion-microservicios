package com.hiberus.show.mixer;

import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.InputPlatformKey;
import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShowMixerApplication.class)
public class InsertData {

    private static final String SHOW = "Tenet";
    private static final String PLATFORM = "HBO";
    //private static final String PLATFORM = "Netflix";

    @Value("${spring.cloud.stream.bindings.shows.destination}")
    private String showsTopic;

    @Value("${spring.cloud.stream.bindings.platforms.destination}")
    private String platformsTopic;

    private final Map<String, Object> properties = new HashMap<>();

    {
        properties.put("key.serializer", SpecificAvroSerializer.class);
        properties.put("value.serializer", SpecificAvroSerializer.class);
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("schema.registry.url", "http://localhost:8081");
    }

    @Test
    public void insertData() throws InterruptedException {
        final InputShowKey inputShowKey = InputShowKey.newBuilder().setId(generateId(SHOW)).build();
        final InputShowEvent inputShowEvent = InputShowEvent.newBuilder().setName(SHOW).setIsan(generateISAN()).build();

        final InputPlatformKey inputPlatformKey = InputPlatformKey.newBuilder().setId(generateId(PLATFORM)).build();
        final InputPlatformEvent inputPlatformEvent = InputPlatformEvent.newBuilder().setIsan(generateISAN()).setPlatform(PLATFORM).build();

        sendMessage(showsTopic, inputShowKey, inputShowEvent);
        sendMessage(platformsTopic, inputPlatformKey, inputPlatformEvent);
    }

    private <K, V> void sendMessage(final String topic, final K key, final V event) throws InterruptedException {
        DefaultKafkaProducerFactory<K, V> producerFactory = new DefaultKafkaProducerFactory<>(properties);
        KafkaTemplate<K, V> kafkaTemplate = new KafkaTemplate<>(producerFactory, true);
        kafkaTemplate.send(topic, key, event);
        kafkaTemplate.flush();
        Thread.sleep(500L);
        log.info("Sent message {} to topic {}", event, topic);
    }

    private String generateISAN() {
        return Integer.toString(Math.abs(SHOW.hashCode()));
    }

    private String generateId(final String text) {
        return Integer.toString(Math.abs(text.hashCode())).substring(0, 5);
    }
}
