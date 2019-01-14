package thrift.server;

import configuraton.ServerConfig;
import event.EventService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.event.impl.EventServiceImpl;

public class ServerService {

    private static final Logger LOGGER = LoggerFactory.getLogger( ServerService.class.getName() );

    private ServerConfig serverConfig;

    public ServerService() {
        this.serverConfig = new ServerConfig();
    }

    public void start() {
        TServerSocket serverTransport = null;
        try {
            serverTransport = new TServerSocket(serverConfig.getServerPort());
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                            .processor(new EventService.Processor<>(new EventServiceImpl())));
            LOGGER.info("Starting server on port :" + serverConfig.getServerPort());
            server.serve();
        } catch (TTransportException e) {
            LOGGER.error("Exception starting server", e.getMessage());
        } finally {
            serverTransport.close();
        }
    }
}
