package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class RpcRequestDecoderHandler extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        byteBuf.markReaderIndex();
        int totalLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < totalLength) {
            byteBuf.resetReaderIndex();
        } else {
            RpcMessage msg = RpcMsgConverter.decode(byteBuf, false);
            log.info("result : {}", msg.getResponse());
        }
    }

}
