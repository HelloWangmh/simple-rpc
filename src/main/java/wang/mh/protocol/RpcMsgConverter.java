package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcMsgConverter {


    public static ByteBuf encode(RpcMessage message, boolean fromClient) {
        byte[] serviceName = message.getServiceName().getBytes();
        byte[] methodName = message.getMethodName().getBytes();
        byte[] response = null;
        int totalLength;
        if (fromClient) {
             totalLength = serviceName.length + methodName.length + 8;
        } else {
            response = message.getResponse().getBytes();
            totalLength = serviceName.length + methodName.length + response.length + 12;
        }
        ByteBuf byteBuf = Unpooled.buffer(totalLength);
        byteBuf.writeInt(totalLength);
        byteBuf.writeInt(serviceName.length);
        byteBuf.writeBytes(serviceName);
        byteBuf.writeInt(methodName.length);
        byteBuf.writeBytes(methodName);
        if (! fromClient) {
            byteBuf.writeInt(response.length);
            byteBuf.writeBytes(response);
        }
        return byteBuf;
    }

    public static RpcMessage decode(ByteBuf byteBuf, boolean fromClient) {
        int serviceNameLength = byteBuf.readInt();
        String serviceName = byteBuf.readBytes(serviceNameLength).toString(CharsetUtil.UTF_8);
        int methodNameLength = byteBuf.readInt();
        String methodName = byteBuf.readBytes(methodNameLength).toString(CharsetUtil.UTF_8);
        log.info("decode message serviceName : {}, methodName : {}", serviceName, methodName);
        RpcMessage msg = new RpcMessage(serviceName, methodName);
        if (! fromClient) {
            int responseLength = byteBuf.readInt();
            String response = byteBuf.readBytes(responseLength).toString(CharsetUtil.UTF_8);
            msg.setResponse(response);
        }
        return msg;
    }
}
