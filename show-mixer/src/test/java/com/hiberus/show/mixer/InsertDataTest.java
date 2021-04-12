package com.hiberus.show.mixer;

import com.hiberus.kafka.manager.KafkaManager;
import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.InputPlatformKey;
import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShowMixerApplication.class)
public class InsertDataTest {

    private static final String SHOW = "Tenet";
    //private static final String PLATFORM = "Filmin";
    private static final String PLATFORM = "HBO";

    @Value("${spring.cloud.stream.bindings.shows.destination}")
    private String showsTopic;

    @Value("${spring.cloud.stream.bindings.platforms.destination}")
    private String platformsTopic;

    @Value("${spring.cloud.stream.bindings.output.destination}")
    private String outputTopic;

    private final KafkaManager kafkaManager = new KafkaManager();

    @Test
    public void receiveData() {
        kafkaManager.receiveRecords(outputTopic, 1);
    }

    @Test
    public void insertPlatform() {
        final InputPlatformKey inputPlatformKey = InputPlatformKey.newBuilder().setId(generateId(PLATFORM)).build();
        final InputPlatformEvent inputPlatformEvent = InputPlatformEvent.newBuilder().setIsan(generateISAN()).setPlatform(PLATFORM).build();

        kafkaManager.sendRecords(platformsTopic, inputPlatformKey, inputPlatformEvent);
    }

    @Test
    public void insertShow() {
        final InputShowKey inputShowKey = InputShowKey.newBuilder().setId(generateId(SHOW)).build();
        final InputShowEvent inputShowEvent = InputShowEvent.newBuilder().setName(SHOW).setIsan(generateISAN()).build();

        kafkaManager.sendRecords(showsTopic, inputShowKey, inputShowEvent);
    }

    private String generateISAN() {
        return Integer.toString(Math.abs(SHOW.hashCode()));
    }

    private String generateId(final String text) {
        return Integer.toString(Math.abs(text.hashCode())).substring(0, 5);
    }
}
