package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcRequestEncoderHandler extends MessageToByteEncoder<RqMessage>{

    @Override
    protected void encode(ChannelHandlerContext chx, RqMessage message, ByteBuf byteBuf) throws Exception {
        byteBuf = RpcMsgConverter.encode(message);
        chx.writeAndFlush(byteBuf);
    }

}
