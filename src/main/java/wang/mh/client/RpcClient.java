package wang.mh.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import wang.mh.protocol.RpcRequestDecoderHandler;
import wang.mh.protocol.RpcRequestEncoderHandler;
import wang.mh.protocol.RqMessage;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RpcClient {

    private final String host;

    private final int port;

    private ConcurrentMap<Long, RpcFuture> rpcFutureMap; //id -->响应结果

    private ChannelFuture channelFuture;

    private EventLoopGroup group;


    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
        rpcFutureMap = new ConcurrentHashMap<>();
    }

    public void start() throws Exception {
        this.group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        GetResultHandler clientHandler = new GetResultHandler(this);
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new RpcRequestEncoderHandler())
                                .addLast(new RpcRequestDecoderHandler())
                                .addLast(clientHandler);
                    }
                });
        channelFuture = bootstrap.connect().sync();
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public RpcFuture send(long id, RqMessage message) {
        RpcFuture rpcFuture = new RpcFuture();
        addFuture(id, rpcFuture);
        channelFuture.channel().writeAndFlush(message);
        return rpcFuture;
    }

    public void addFuture(long id, RpcFuture future) {
        rpcFutureMap.put(id, future);
    }

    public RpcFuture getFuture(long id) {
        return rpcFutureMap.get(id);
    }

    public RpcFuture removeAndGetFuture(long id) {
        return rpcFutureMap.remove(id);
    }
}
