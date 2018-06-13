package wang.mh.protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RqMessage implements Serializable {

    private long id;

    private String serviceName;

    private String methodName;

    private Object[] args;

    public RqMessage(long id, String serviceName, String methodName, Object[] args) {
        this.id = id;
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.args = args;
    }
}
