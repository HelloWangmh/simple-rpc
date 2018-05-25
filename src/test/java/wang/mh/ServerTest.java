package wang.mh;

import org.junit.Test;
import wang.mh.server.RpcServer;

import java.lang.reflect.Method;

public class ServerTest {

    @Test
    public void startServer() throws Exception {
        RpcServer server = new RpcServer(8080);
        server.start();
    }


    @Test
    public void reflect() throws Exception {
        ComputeServiceImpl compute = new ComputeServiceImpl();
        Class<? extends ComputeServiceImpl> clazz = compute.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> type : parameterTypes) {
                System.out.println(type.getSimpleName());
            }
            System.out.println(method.invoke(compute, 1));
        }
    }
}
