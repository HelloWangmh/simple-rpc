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
        RpcMsgConverter.decode(byteBuf, list, true);
    }


}
