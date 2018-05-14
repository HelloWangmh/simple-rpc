package wang.mh;

import lombok.extern.slf4j.Slf4j;
import wang.mh.server.RpcServer;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer(8080);
        server.start();
    }

}
