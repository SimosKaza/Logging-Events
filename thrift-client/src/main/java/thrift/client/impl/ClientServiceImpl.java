package thrift.client.impl;

import event.EventResource;
import event.EventService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thrift.client.ClientConfig;
import thrift.client.ClientService;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Objects;

public class ClientServiceImpl implements ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger( ClientService.class.getName() );

    private SecureRandom rand;

    private ClientConfig clientConfig;

    public ClientServiceImpl() {
        this.rand = new SecureRandom();
        this.clientConfig = new ClientConfig();
    }

    public void start() {
        TSocket transport = null;
        try {
            transport = openSocket();
            EventService.Client client = new EventService.Client(new TBinaryProtocol(transport));
            for (int i = 0; i < this.clientConfig.getNumberEvents(); i++) {
                client.handlEvent(generateRandomEvent());
            }
        } catch (TException e) {
            LOGGER.error("ClientService can not handle Event", e.getMessage());
        } finally {
            if(Objects.nonNull(transport))
                transport.close();
        }
    }

    private TSocket openSocket()  {
        TSocket transport = new TSocket( this.clientConfig.getClientHost(), this.clientConfig.getClientPort());
        try {
            transport.open();
        } catch (TTransportException e) {
            LOGGER.error("Can not open Socket", e.getMessage());
        }
        return transport;
    }

    private EventResource generateRandomEvent() {
        return new EventResource((short) 1, new Date().getTime(),
                RandomStringUtils.randomAlphabetic(rand.nextInt(this.clientConfig.getLengthMessage())));
    }

    // First try
    public String generateString() {
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
        int length = rand.nextInt(50) ;
        int alphabetLength = ALPHABET.length() ;
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(rand.nextInt(alphabetLength)));
        }
        return builder.toString();
    }

}
