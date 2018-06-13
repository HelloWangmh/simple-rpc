package wang.mh.register;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegisterService {

    private ConcurrentHashMap<String, ServiceInfo> serviceMap;

    private static RegisterService instance;

    private RegisterService() {
        serviceMap = new ConcurrentHashMap<>();
    }

    public static RegisterService getInstance() {
        if (instance == null) {
            synchronized (RegisterService.class) {
                if (instance == null) {
                    instance = new RegisterService();
                }
            }
        }
        return instance;
    }

    public ServiceInfo getService(String serviceName, String methodName) {
        return serviceMap.get(buildKey(serviceName, methodName));
    }

    public void registerMuti(List<ServiceInfo> serviceInfos) {
        for (ServiceInfo serviceInfo : serviceInfos) {
            register(serviceInfo);
        }
    }
    public void register(ServiceInfo serviceInfo) {
        serviceMap.put(buildKey(serviceInfo.getServiceName(), serviceInfo.getMethodName()), serviceInfo);
    }

    private String buildKey(String serviceName, String methodName) {
        return serviceName + ":" + methodName;
    }
}
