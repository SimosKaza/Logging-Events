package service.kafka.impl;

import event.EventResource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.kafka.KafkaService;
import service.kafka.ProducerCreator;

public class KafkaServiceImpl implements KafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger( KafkaService.class.getName() );

    private ProducerCreator producerCreator;

    public KafkaServiceImpl() {
        this.producerCreator = new ProducerCreator();
    }

    public void push(EventResource event) {
        try {
            this.producerCreator.getProducer().send(new ProducerRecord<Long, EventResource>("testTopic", event));
        } catch (Exception e) {
            LOGGER.error("Kafka producer can not send event", e.getMessage());
            this.producerCreator.close();
        }

    }
}
