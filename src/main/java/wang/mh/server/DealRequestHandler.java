package wang.mh.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import wang.mh.protocol.RqMessage;
import wang.mh.protocol.RsMessage;
import wang.mh.register.RegisterService;
import wang.mh.register.ServiceInfo;

import java.lang.reflect.Method;


@Slf4j
@ChannelHandler.Sharable
public class DealRequestHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RqMessage rq = (RqMessage) msg;
        log.info("receive service : {}, method : {}", rq.getServiceName(), rq.getMethodName());
        ServiceInfo service = RegisterService.getInstance().getService(rq.getServiceName(), rq.getMethodName());
        Method method = service.getMethod();
        Object result = method.invoke(service.getObj(), rq.getArgs());
        RsMessage rs = new RsMessage();//TODO
        rs.success(rq.getId(), result);
        ctx.channel().writeAndFlush(rs);
    }

}
