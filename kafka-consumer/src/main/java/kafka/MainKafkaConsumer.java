package kafka;

import database.CassandraConnector;
import database.DAO.EventResourceDAO;

public class MainKafkaConsumer {

    public static void main(String[] args) {

        ConsumerCreator kafkaConsumer = new ConsumerCreator();
        CassandraConnector cassandraConnector = new CassandraConnector();
        EventResourceDAO eventResourceDAO = new EventResourceDAO();

        KafkaService serviceConsumer = new KafkaService(kafkaConsumer, cassandraConnector, eventResourceDAO);
        serviceConsumer.start();
    }

}
