package wang.mh.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import wang.mh.protocol.RsMessage;

@Slf4j
public class GetResultHandler extends ChannelInboundHandlerAdapter {

    private RpcClient client;
    public GetResultHandler(RpcClient client) {
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RsMessage result = (RsMessage) msg;
        RpcFuture future = client.getFuture(result.getId());
        future.success(result.getResult());
    }
}
