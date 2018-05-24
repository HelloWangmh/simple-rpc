package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *  将字节转化为Rpc Message
 */
@Slf4j
public class RpcResponseDecoderHandler extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext chx, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        int totalLength = byteBuf.readInt();
        //检测是否已接收了足够的字节数
        if (byteBuf.readableBytes() < totalLength) {
            byteBuf.resetReaderIndex();
        } else {
            list.add(RpcMsgConverter.decode(byteBuf, true));
        }
    }


}
