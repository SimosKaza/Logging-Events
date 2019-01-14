package service.event.impl;

import event.EventResource;
import event.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.kafka.impl.KafkaServiceImpl;

public class EventServiceImpl implements EventService.Iface{

    private static final Logger LOGGER = LoggerFactory.getLogger( EventServiceImpl.class.getName() );

    private KafkaServiceImpl kafkaService;

    public EventServiceImpl() {
        this.kafkaService = new KafkaServiceImpl();
    }

    @Override
    public void handlEvent(EventResource event) {
        LOGGER.info(event.toString());
        this.kafkaService.push(event);
    }
}

