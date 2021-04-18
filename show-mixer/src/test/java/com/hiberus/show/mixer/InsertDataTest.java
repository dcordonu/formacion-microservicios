package com.hiberus.show.mixer;

import com.hiberus.kafka.manager.KafkaManager;
import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.InputPlatformKey;
import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class InsertDataTest {

    private static final String SHOW = "Firefly";
    //private static final String SHOW = "Tenet";

    //private static final String PLATFORM = "Filmin";
    private static final String PLATFORM = "HBO";

    private static final String SHOWS_TOPIC = "show-mixer-input-shows";
    private static final String PLATFORMS_TOPIC = "show-mixer-input-platforms";
    private static final String OUTPUT_TOPIC = "show-mixer-output";

    @Test
    public void receiveData() {
        KafkaManager.receiveRecords(OUTPUT_TOPIC, 1);
    }

    @Test
    public void addPlatform() {
        final InputPlatformKey inputPlatformKey = InputPlatformKey.newBuilder().setId(generateId(PLATFORM)).build();
        final InputPlatformEvent inputPlatformEvent = InputPlatformEvent.newBuilder()
                .setIsan(generateISAN())
                .setPlatform(PLATFORM)
                .build();

        KafkaManager.sendRecords(PLATFORMS_TOPIC, inputPlatformKey, inputPlatformEvent);
    }

    @Test
    public void addShow() {
        final InputShowKey inputShowKey = InputShowKey.newBuilder().setId(generateId(SHOW)).build();
        final InputShowEvent inputShowEvent = InputShowEvent.newBuilder()
                .setName(SHOW)
                .setIsan(generateISAN())
                .build();

        KafkaManager.sendRecords(SHOWS_TOPIC, inputShowKey, inputShowEvent);
    }

    @Test
    public void updateShow() {
        final InputShowKey inputShowKey = InputShowKey.newBuilder().setId(generateId(SHOW)).build();
        final InputShowEvent inputShowEvent = InputShowEvent.newBuilder()
                .setName(SHOW + " (dCut)")
                .setIsan(generateISAN())
                .build();

        KafkaManager.sendRecords(SHOWS_TOPIC, inputShowKey, inputShowEvent);
    }

    @Test
    public void deletePlatform() {
        final InputPlatformKey inputPlatformKey = InputPlatformKey.newBuilder().setId(generateId(PLATFORM)).build();
        final InputPlatformEvent inputPlatformEvent = InputPlatformEvent.newBuilder()
                .setIsan(generateISAN())
                .setPlatform(PLATFORM)
                .build();

        KafkaManager.sendRecords(PLATFORMS_TOPIC, inputPlatformKey, inputPlatformEvent);
    }

    private String generateISAN() {
        return Integer.toString(Math.abs(SHOW.hashCode()));
    }

    private String generateId(final String text) {
        return Integer.toString(Math.abs(text.hashCode())).substring(0, 5);
    }
}
