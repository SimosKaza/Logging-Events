package thrift.client;

import thrift.client.impl.ClientServiceImpl;

public class MainClient {
    public static void main(String[] args) {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.start();
    }
}

