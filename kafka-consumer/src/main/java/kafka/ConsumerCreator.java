package kafka;

import event.EventResource;
import kafka.Constants.KafkaConstants;
import kafka.configuraton.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Objects;

public class ConsumerCreator {

    private Consumer consumer;
    private KafkaConsumerConfig config;

    public ConsumerCreator() {
        this.consumer = createConsumer();
        this.config = new KafkaConsumerConfig();
    }

    public Consumer<Long, EventResource> createConsumer() {
        Consumer<Long, EventResource> consumer = new KafkaConsumer<>(this.config.getPropsEnv());
        consumer.subscribe(Collections.singletonList(KafkaConstants.TOPIC_NAME));
        return consumer;
    }

    public void close(){
        if (Objects.nonNull(consumer)){
            consumer.close();
        }
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
