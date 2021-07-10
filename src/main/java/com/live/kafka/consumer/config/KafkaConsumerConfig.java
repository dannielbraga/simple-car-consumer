package com.live.kafka.consumer.config;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.live.kafka.consumer.dto.CarDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.group-id}")
    private String gropuId;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, CarDTO> carConsumerFactory(){
        Map<String, Object> configsProps = new HashMap<>();
        configsProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configsProps.put(ConsumerConfig.GROUP_ID_CONFIG, gropuId);
        configsProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configsProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configsProps, new StringDeserializer(),
                new org.springframework.kafka.support.serializer.JsonDeserializer<>(CarDTO.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CarDTO> carKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, CarDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(carConsumerFactory());
        return factory;
    }
}
