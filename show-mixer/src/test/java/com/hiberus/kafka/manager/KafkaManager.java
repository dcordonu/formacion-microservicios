package com.hiberus.kafka.manager;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KafkaManager {

    private static final String CONSUMER_ID = "show-test";
    private static final Object SERIALIZER = SpecificAvroSerializer.class;
    private static final Object DESERIALIZER = SpecificAvroDeserializer.class;
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

    private final Map<String, KafkaTemplate<Object, Object>> producerTemplates = new HashMap<>();

    private final Map<String, Object> producerProperties = new HashMap<>();
    private final Map<String, Object> consumerProperties = new HashMap<>();

    {
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, SERIALIZER);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SERIALIZER);
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        producerProperties.put("schema.registry.url", SCHEMA_REGISTRY_URL);

        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, DESERIALIZER);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DESERIALIZER);
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        consumerProperties.put("schema.registry.url", SCHEMA_REGISTRY_URL);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_ID);
    }

    @SuppressWarnings("unchecked")
    public <K, V> void sendRecords(final String topic, final K key, final V event) {
        this.producerTemplates.putIfAbsent(topic, buildProducerFactory());
        final KafkaTemplate<K, V> kafkaTemplate = (KafkaTemplate<K, V>) this.producerTemplates.get(topic);
        kafkaTemplate.send(topic, key, event);
        log.info("Sent message {} to topic {}", event, topic);
    }

    @SuppressWarnings("unchecked")
    public <K, V> ConsumerRecords<K, V> receiveRecords(final String topic, final int seconds) {
        final KafkaConsumer<K, V> consumer = buildConsumer(topic);

        final ConsumerRecords<K, V> records = consumer.poll(Duration.ofSeconds(seconds));
        consumer.commitAsync();

        return records;
    }

    private <K, V> KafkaConsumer<K, V> buildConsumer(final String topic) {
        final KafkaConsumer<K, V> kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        // Wait until consumer has partitions assigned by Kafka
        while (kafkaConsumer.assignment().isEmpty()) {
            kafkaConsumer.poll(Duration.ofMillis(10L));
        }

        return kafkaConsumer;
    }

    private <K, V> KafkaTemplate<K, V> buildProducerFactory() {
        final ProducerFactory<K, V> producerFactory = new DefaultKafkaProducerFactory<>(producerProperties);
        return new KafkaTemplate<>(producerFactory, true);
    }
}
