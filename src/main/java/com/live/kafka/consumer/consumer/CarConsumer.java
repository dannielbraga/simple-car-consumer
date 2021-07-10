package com.live.kafka.consumer.consumer;

import com.live.kafka.consumer.dto.CarDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CarConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CarConsumer.class);

    @Value(value = "${topic.name}")
    private String topic;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.group-id}",
            containerFactory = "carKafkaListenerContainerFactory")
    public void listenerTopicCar(ConsumerRecord<String, CarDTO> record){
        logger.info("Received Message in partition " + record.partition() + "| Message: " + record.value());
    }
}
