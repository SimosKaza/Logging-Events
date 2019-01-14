package kafka;

import database.CassandraConnector;
import database.DAO.EventResourceDAO;
import event.EventResource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KafkaServiceTest {

    MockConsumer<Long, EventResource> consumer;

    @InjectMocks
    KafkaService myConsumer;

    @Mock
    ConsumerCreator kafkaConsumer;

    @Mock
    CassandraConnector cassandraConnector;

    @Mock
    EventResourceDAO eventResourceDAO;

    @Before
    public void setUp() {
        consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    }


    @Test
    public void testConsumer() {

        consumer.assign(Arrays.asList(new TopicPartition("testTopic", 0)));

        HashMap<TopicPartition, Long> beginningOffsets = new HashMap<>();
        beginningOffsets.put(new TopicPartition("testTopic", 0), 0L);
        consumer.updateBeginningOffsets(beginningOffsets);
        consumer.addRecord(new ConsumerRecord<>("testTopic",
                0, 0L, 1L, new EventResource((short) 1, new Date().getTime(), "123")));
        consumer.addRecord(new ConsumerRecord<>("testTopic",
                0, 1L, 1L, new EventResource((short) 1, new Date().getTime(), "1223")));
        Mockito.when(kafkaConsumer.getConsumer()).thenReturn(consumer);

        myConsumer.start();
        Assert.assertTrue(consumer.closed());
    }
}