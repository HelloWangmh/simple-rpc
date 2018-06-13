package wang.mh.protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RsMessage implements Serializable {

    private long id;

    private Object result;

    private Boolean success;  //

    private String errorMsg; //失败原因

    public void success(long id, Object result) {
        this.id = id;
        this.result = result;
        this.success = Boolean.TRUE;
    }

    public void fail(long id, String errorMsg) {
        this.id = id;
        this.errorMsg = errorMsg;
        this.success = Boolean.FALSE;
    }
}
