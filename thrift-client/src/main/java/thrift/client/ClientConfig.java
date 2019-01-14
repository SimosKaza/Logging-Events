package thrift.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger( ClientConfig.class.getName() );


    private String clientHost;

    private int clientPort;

    private int numberEvents;

    private int lengthMessage;

    public ClientConfig(){
        getPropertiesData();
    }

    private void getPropertiesData() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);
            this.clientHost = prop.getProperty("thrift.client.host");
            this.clientPort = Integer.parseInt(prop.getProperty("thrift.client.port"));
            this.numberEvents = Integer.parseInt(prop.getProperty("number.of.events"));
            this.lengthMessage = Integer.parseInt(prop.getProperty("length.random.message"));
        } catch (IOException e) {
            LOGGER.error("ClientConfig ClientConfig property error!", e.getMessage());
        } finally {
            if (Objects.nonNull(input)) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error("ClientConfig IOException close InputStream", e.getMessage());
                }
            }
        }
    }

    public String getClientHost() {
        return clientHost;
    }

    public int getClientPort() {
        return clientPort;
    }

    public int getNumberEvents() {
        return numberEvents;
    }

    public int getLengthMessage() {
        return lengthMessage;
    }
}
