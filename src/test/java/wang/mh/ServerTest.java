package wang.mh;

import org.junit.Test;
import wang.mh.register.RegisterService;
import wang.mh.register.ServiceInfo;
import wang.mh.server.RpcServer;
import wang.mh.service.ComputeService;
import wang.mh.service.ComputeServiceImpl;

import java.util.List;


public class ServerTest {

    @Test
    public void startServer() throws Exception {
        RpcServer server = new RpcServer(8080);
        RegisterService service = RegisterService.getInstance();
        service.registerMuti(ServiceInfo.newServiceInfos(ComputeServiceImpl.class));
        server.start();
    }
}
