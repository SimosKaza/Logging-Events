Back-End Code Challenge

Abstract:There are three basic modules. The first one, the"thrift-client", is the program that auto-generates 100
logging events and sends them to a server ( the second module that is the "thrift-server"). The second module has
a server that accepts the logging events. Also, there is a Kafka Producer, who provides the "send" method, in order
to push messages to a topic. Finally, the third module is a Kafka client, that consumes the log events from a Kafka
cluster and writes them to a Cassandra database.


First of all, we have to start Cassandra in order to connect with the Database
    • su - cassandra (or sudo su)
    • /usr/local/cassandra/bin/cassandra -f
    • /usr/local/cassandra/bin/cqlsh localhost
      
Also, we  have to create the keyspace and our table. I created a table with the minimum set of attributes and
set the attribute "time" as a primary key.
    • create keyspace logEvents with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
    • USE logevents ;
    • create table event(version smallint, time bigint PRIMARY KEY, message text);
	
Compiling Thrift definition file and generate code (The "events.thrift" file is into "thrift-service/thrift" path)
    • /thrift-0.11.0/compiler/cpp/thrift -r --gen java events.thrift 

I have set the time’s type as i64, because when I generate the time by using the “new Date().getTime()”
it returns a long value. In Java, long is 64 bits for any JVM.

The generated code has been set in a fourth module. Thus, both modules thrift-server and
kafka-consumer are able to include the generated Code.

Also, The kafka-consumer module has a few unit tests.

Starting Zookeeper and Kafka server and create a topic
    • ./bin/zookeeper-server-start.sh config/zookeeper.properties
    • ./bin/kafka-server-start.sh config/server.properties
    • ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic testTopic
    • ./bin/kafka-topics.sh --list --zookeeper localhost:2181 


Run from command line:

java -jar thrift-server/target/thrift-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar 

java -jar kafka-consumer/target/kafka-consumer-0.0.1-SNAPSHOT-jar-with-dependencies.jar 

java -jar thrift-client/target/thrift-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar


I have used the following versions: 
    • Java 1.8.0_181
    • Thrift 0.11.0
    • Kafka 2.12-2.1.0
    • Cassandra 3.11.3
    • Maven 3
    • Junit 4
