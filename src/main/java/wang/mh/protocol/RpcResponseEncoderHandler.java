package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.Serializable;
import java.util.List;

/**
 *  将服务端的响应信息转化为字节
 */
public class RpcResponseEncoderHandler extends MessageToMessageEncoder<Serializable>{

    @Override
    protected void encode(ChannelHandlerContext ctx, Serializable msg, List<Object> out) throws Exception {
        ByteBuf buffer = RpcMsgConverter.encode(msg);
        out.add(buffer);
    }

}
