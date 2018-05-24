package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcRequestEncoderHandler extends MessageToByteEncoder<RpcMessage>{

    @Override
    protected void encode(ChannelHandlerContext chx, RpcMessage message, ByteBuf byteBuf) throws Exception {
        log.info("encode message serviceName : {}, methodName : {}", message.getServiceName(), message.getMethodName());
        byteBuf = RpcMsgConverter.encode(message, true);
        chx.writeAndFlush(byteBuf);
    }

}
