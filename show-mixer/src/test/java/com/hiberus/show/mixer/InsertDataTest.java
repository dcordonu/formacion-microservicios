package com.hiberus.show.mixer;

import com.hiberus.kafka.manager.KafkaManager;
import com.hiberus.show.library.InputPlatformEvent;
import com.hiberus.show.library.InputPlatformKey;
import com.hiberus.show.library.InputShowEvent;
import com.hiberus.show.library.InputShowKey;
import com.hiberus.show.library.OutputShowPlatformListEvent;
import com.hiberus.show.library.OutputShowPlatformListKey;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

public class InsertDataTest {

    private static final String SHOW = "Firefly";
    //private static final String SHOW = "Tenet";

    //private static final String PLATFORM = "Filmin";
    //private static final String PLATFORM = "HBO";
    private static final String PLATFORM = "Netflix";

    private static final String SHOWS_TOPIC = "show-mixer-input-shows";
    private static final String PLATFORMS_TOPIC = "show-mixer-input-platforms";
    private static final String OUTPUT_TOPIC = "show-mixer-output";

    @Before
    public void init() {
        KafkaManager.initConsumers(OUTPUT_TOPIC);
    }

    @Test
    public void testHappyPath() {
        final String show = "Tenet";
        final String platform = "Netflix";

        addShow(show);
        addPlatform(show, platform);

        final ConsumerRecords<OutputShowPlatformListKey, OutputShowPlatformListEvent> records =
                KafkaManager.receiveRecords(OUTPUT_TOPIC, Duration.ofSeconds(2));

        Assert.assertEquals(2, records.count());
    }

    @Test
    public void addShow() {
        addShow(SHOW);
    }

    @Test
    public void addPlatform() {
        addPlatform(SHOW, PLATFORM);
    }

    private void addShow(final String show) {
        final InputShowKey inputShowKey = InputShowKey.newBuilder()
                .setId(generateId(show))
                .build();
        final InputShowEvent inputShowEvent = InputShowEvent.newBuilder()
                .setName(show)
                .setIsan(generateISAN(show))
                .build();

        KafkaManager.sendRecord(SHOWS_TOPIC, inputShowKey, inputShowEvent);
    }

    private void addPlatform(final String show, final String platform) {
        final InputPlatformKey inputPlatformKey = InputPlatformKey.newBuilder()
                .setId(generateId(platform))
                .build();
        final InputPlatformEvent inputPlatformEvent = InputPlatformEvent.newBuilder()
                .setPlatform(platform)
                .setIsan(generateISAN(show))
                .build();

        KafkaManager.sendRecord(PLATFORMS_TOPIC, inputPlatformKey, inputPlatformEvent);
    }

    private String generateISAN(final String show) {
        return Integer.toString(Math.abs(show.hashCode()));
    }

    private String generateId(final String text) {
        return Integer.toString(Math.abs(text.hashCode())).substring(0, 5);
    }
}
