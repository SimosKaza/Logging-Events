import thrift.server.ServerService;

public class MainServer
{
    public static void main(String[] args)
    {
        ServerService service = new ServerService();
        service.start();
    }
}