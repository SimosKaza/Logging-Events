package service.kafka;

import event.EventResource;

public interface KafkaService {

    void push(EventResource event);

}
