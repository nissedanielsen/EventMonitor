package com.example.kafkamsconsumer.config;

import com.example.kafkamsconsumer.mapper.TransactionDeserializer;
import com.example.kafkamsconsumer.mapper.TransactionSerializer;
import com.example.kafkamsconsumer.model.Transaction;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaStreamConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean(name = "defaultKafkaStreamsConfig")
    public KafkaStreamsConfiguration kafkaStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "ms-consumer-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        return new KafkaStreamsConfiguration(props);
    }


    @Bean
    public KStream<String, Transaction> kStream(StreamsBuilder builder) {
        Serde<Transaction> serde = Serdes.serdeFrom(new TransactionSerializer(), new TransactionDeserializer());

        // create stream from topic-input
        KStream<String, Transaction> stream = builder.stream("topic-input", Consumed.with(Serdes.String(), serde));

        // send all transactions to topic-all-transactions
        stream.to("topic-all-transactions", Produced.with(Serdes.String(), serde));

        // filter and send to high-value-transactions
        stream.filter((key, value) -> value.getAmount() > 2500)
                .to("topic-high-value-transactions", Produced.with(Serdes.String(), serde));

        return stream;
    }


    @Bean
    public StreamsBuilder streamsBuilder() {
        return new StreamsBuilder();
    }

    @Bean
    public KafkaStreams kafkaStreams(StreamsBuilder builder, KafkaStreamsConfiguration config) {
        // Create a KafkaStreams instance using the StreamsBuilder and configuration
        KafkaStreams streams = new KafkaStreams(builder.build(), config.asProperties());

        // Start the streams application
        streams.start();

        return streams;
    }
}
