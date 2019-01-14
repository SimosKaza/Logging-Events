package configuraton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ServerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger( ServerConfig.class.getName() );


    private String ServerHost;

    private int ServerPort;

    public ServerConfig(){
        getPropertiesData();
    }

    private void getPropertiesData() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);
            this.ServerHost = prop.getProperty("thrift.server.host");
            this.ServerPort = Integer.parseInt(prop.getProperty("thrift.server.port"));
        } catch (IOException e) {
            LOGGER.error("IOException, Server Configuration properties ", e.getMessage() );
        } finally {
            if (Objects.nonNull(input)) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error("IOException, Server Configuration error close stream ", e.getMessage() );
                }
            }
        }
    }

    public String getServerHost() {
        return ServerHost;
    }

    public int getServerPort() {
        return ServerPort;
    }
}

