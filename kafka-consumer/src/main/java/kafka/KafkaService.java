package kafka;

import database.CassandraConnector;
import database.DAO.EventResourceDAO;
import event.EventResource;
import kafka.Constants.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger( KafkaService.class.getName() );

    private ConsumerCreator kafkaConsumer;
    private CassandraConnector cassandraConnector;
    private EventResourceDAO eventResourceDAO;

    public KafkaService(ConsumerCreator kafkaConsumer, CassandraConnector cassandraConnector, EventResourceDAO eventResourceDAO) {
        this.kafkaConsumer = kafkaConsumer;
        this.cassandraConnector = cassandraConnector;
        this.eventResourceDAO = eventResourceDAO;
    }

    public void start() {
        ConsumerRecords<Long, EventResource> consumerRecords;
        int noMessageFound = 0;
        while (true) {
            consumerRecords = this.kafkaConsumer.getConsumer().poll( KafkaConstants.MAX_POLL_RECORDS);
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > KafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                    // If no message found count is reached to threshold exit loop.
                    break;
                else
                    continue;
            }
            //print each record.
            for (ConsumerRecord<Long, EventResource> record : consumerRecords) {
                LOGGER.info(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(),
                                            record.partition(), record.value()));
                this.eventResourceDAO.insert(cassandraConnector.getSession(), record.value());
            }
            // commits the offset of record to broker.
            this.kafkaConsumer.getConsumer().commitAsync();
        }
        this.kafkaConsumer.getConsumer().close();
        this.cassandraConnector.close();
    }
}
