package wang.mh.protocol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RpcMessage {

    private String serviceName;

    private String methodName;

    private String response;

    public RpcMessage(String serviceName, String methodName) {
        this.serviceName = serviceName;
        this.methodName = methodName;
    }
}
