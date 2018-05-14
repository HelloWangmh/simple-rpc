package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.List;

/**
 *  将字节转化为Rpc Message
 */
@Slf4j
public class RpcDecoderHandler extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext chx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int totalLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < totalLength) {
            byteBuf.resetReaderIndex();
        } else {
            list.add(convert2Message(byteBuf));
        }
    }

    private RpcMessage convert2Message(ByteBuf byteBuf) {
        int serviceNameLength = byteBuf.readInt();
        String serviceName = byteBuf.readBytes(serviceNameLength).toString(CharsetUtil.UTF_8);
        int methodNameLength = byteBuf.readInt();
        String methodName = byteBuf.readBytes(methodNameLength).toString(CharsetUtil.UTF_8);
        log.info("decode message serviceName : {}, methodName : {}", serviceName, methodName);
        return new RpcMessage(serviceName, methodName);
    }
}
