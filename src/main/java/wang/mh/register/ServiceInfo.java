package wang.mh.register;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class ServiceInfo {

    private String serviceName; //接口名

    private String methodName; //方法名

    private Object obj;  //实例

    private Method method;

    public static List<ServiceInfo> newServiceInfos(Class clazz) throws IllegalAccessException, InstantiationException {
        String serviceName = clazz.getName();
        Object obj = clazz.newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        List<ServiceInfo> serviceInfos = Arrays.stream(methods)
                .map(m -> genServiceInfo(serviceName, obj, m))
                .collect(Collectors.toList());
        return serviceInfos;
    }

    private static ServiceInfo genServiceInfo(String serviceName, Object obj, Method method) {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceName(serviceName);
        serviceInfo.setObj(obj);
        serviceInfo.setMethodName(method.getName());
        serviceInfo.setMethod(method);
        return serviceInfo;
    }
}
