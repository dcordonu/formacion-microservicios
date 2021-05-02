package com.hiberus.kafka.manager;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.awaitility.core.ConditionTimeoutException;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.awaitility.Awaitility.await;

@Slf4j
public class KafkaManager {

    private static final Object SERIALIZER = SpecificAvroSerializer.class;
    private static final Object DESERIALIZER = SpecificAvroDeserializer.class;
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

    private static final Map<String, Object> producerProperties = new HashMap<>();
    private static final Map<String, Object> consumerProperties = new HashMap<>();

    private static final Map<String, KafkaTemplate<Object, Object>> producerTemplates = new HashMap<>();
    private static final Map<String, KafkaConsumer<Object, Object>> consumers = new HashMap<>();

    static {
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, SERIALIZER);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SERIALIZER);
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        producerProperties.put("schema.registry.url", SCHEMA_REGISTRY_URL);

        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, DESERIALIZER);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DESERIALIZER);
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        consumerProperties.put("schema.registry.url", SCHEMA_REGISTRY_URL);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "show-microservice-test");
    }

    @SuppressWarnings("unchecked")
    public static <K, V> void sendRecord(final String topic, final K key, final V value) {
        producerTemplates.putIfAbsent(topic, buildProducerFactory());
        final KafkaTemplate<K, V> kafkaTemplate = (KafkaTemplate<K, V>) producerTemplates.get(topic);
        kafkaTemplate.send(topic, key, value);
        log.info("Sent message {} to topic {}", value, topic);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> void initConsumers(final String... topics) {
        for (String topic : topics) {
            final KafkaConsumer<K, V> consumer = buildConsumer();

            consumer.subscribe(Collections.singletonList(topic));

            try {
                await().atMost(Duration.ofSeconds(5)).until(() -> !consumer.assignment().isEmpty());
            } catch (final ConditionTimeoutException e) {
                log.error("Could not assign partitions for consumer in topic {}", topic);
                throw new RuntimeException();
            }

            consumer.seekToEnd(consumer.assignment());

            consumers.put(topic, (KafkaConsumer<Object, Object>) consumer);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> ConsumerRecords<K, V> receiveRecords(final String topic, final Duration waitUntil) {
        final Consumer<K, V> consumer = (Consumer<K, V>) consumers.get(topic);
        final ConsumerRecords<K, V> records = consumer.poll(waitUntil);

        consumer.commitSync();
        consumer.close();

        return records;
    }

    private static <K, V> KafkaConsumer<K, V> buildConsumer() {
        return new KafkaConsumer<>(consumerProperties);
    }

    private static <K, V> KafkaTemplate<K, V> buildProducerFactory() {
        final ProducerFactory<K, V> producerFactory = new DefaultKafkaProducerFactory<>(producerProperties);
        return new KafkaTemplate<>(producerFactory, true);
    }
}
