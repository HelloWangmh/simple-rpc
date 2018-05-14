package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcEncoderHandler extends MessageToByteEncoder<RpcMessage>{

    @Override
    protected void encode(ChannelHandlerContext chx, RpcMessage message, ByteBuf byteBuf) throws Exception {
        byte[] serviceName = message.getServiceName().getBytes();
        byte[] methodName = message.getMethodName().getBytes();
        byteBuf.writeInt(serviceName.length + methodName.length + 8);
        byteBuf.writeInt(serviceName.length);
        byteBuf.writeBytes(serviceName);
        byteBuf.writeInt(methodName.length);
        byteBuf.writeBytes(methodName);
        log.info("encode message serviceName : {}, methodName : {}", message.getServiceName(), message.getMethodName());
        chx.writeAndFlush(byteBuf);
    }

}
