package service.kafka;

import configuraton.KafkaProducerConfig;
import event.EventResource;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import java.util.Objects;

public class ProducerCreator {

    private Producer producer;
    private KafkaProducerConfig config;

    public ProducerCreator() {
        this.producer = createProducer();
        this.config = new KafkaProducerConfig();
    }

    public Producer<Long, EventResource> createProducer() {
        return new KafkaProducer<>(config.getPropsEnv());
    }

    public void close(){
        if (Objects.nonNull(producer)){
            producer.close();
        }
    }

    public Producer getProducer() {
        return producer;
    }
}
