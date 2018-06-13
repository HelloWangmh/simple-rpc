package wang.mh.client;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import wang.mh.protocol.RqMessage;
import wang.mh.utils.IDGenerator;

import java.lang.reflect.Method;

@Slf4j
@SuppressWarnings("unchecked")
public class RpcProxy implements MethodInterceptor {

    private RpcClient rpcClient;

    private RpcProxy(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    public static <T> T getProxy(RpcClient rpcClient, Class clazz) {
        Enhancer en = new Enhancer();
        en.setSuperclass(clazz);
        en.setCallback(new RpcProxy(rpcClient));
        return (T) en.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (method.getDeclaringClass().getSimpleName().equals("Object")) {
            return methodProxy.invokeSuper(obj, args);
        }
        Long callId = IDGenerator.instance().getId();
        String serviceName = method.getDeclaringClass().getName();
        String methodName = method.getName();
        RqMessage rq = new RqMessage(callId, serviceName, methodName, args);
        RpcFuture future = rpcClient.send(callId, rq);
        return future.get();
    }
}
