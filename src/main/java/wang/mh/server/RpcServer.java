package wang.mh.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import wang.mh.protocol.RpcResponseDecoderHandler;
import wang.mh.protocol.RpcResponseEncoderHandler;

@Slf4j
public class RpcServer {
    private int port;

    public RpcServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
       try {
           bootstrap.group(group)
                   .channel(NioServerSocketChannel.class)
                   .localAddress(port)
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       public void initChannel(SocketChannel ch) throws Exception {
                           ch.pipeline()
                                   .addLast(new RpcResponseDecoderHandler())
                                   .addLast(new DealRequestHandler())
                                   .addLast(new RpcResponseEncoderHandler());
                       }
                   });
           ChannelFuture future = bootstrap.bind().sync();
           log.info("RpcServer has started and listening for connections on : {}", future.channel().localAddress());
           future.channel().closeFuture().sync();
       } finally {
           group.shutdownGracefully();
       }
    }
}
