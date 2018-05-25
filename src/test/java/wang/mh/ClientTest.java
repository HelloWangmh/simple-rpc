package wang.mh;

import org.junit.Test;
import wang.mh.client.RpcClient;

public class ClientTest {

    @Test
    public void start() throws Exception {
        RpcClient client = new RpcClient("localhost", 8080);
        client.start();
    }

}
