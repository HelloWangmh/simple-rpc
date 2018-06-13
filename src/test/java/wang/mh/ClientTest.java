package wang.mh;

import org.junit.Assert;
import org.junit.Test;
import wang.mh.client.RpcClient;
import wang.mh.client.RpcProxy;
import wang.mh.service.ComputeService;
import wang.mh.service.ComputeServiceImpl;

public class ClientTest {

    @Test
    public void start() throws Exception {
        RpcClient client = new RpcClient("localhost", 8080);
        client.start();
        ComputeService computeService = (ComputeServiceImpl)RpcProxy.getProxy(client, ComputeServiceImpl.class);
        int result = computeService.addOne(1);
        Assert.assertEquals(2, result);
        client.stop();
    }

}
