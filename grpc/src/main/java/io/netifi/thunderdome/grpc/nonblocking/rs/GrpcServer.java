package io.netifi.thunderdome.grpc.nonblocking.rs;

import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;
import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleResponse;
import io.netifi.testing.protobuf.SimpleServiceGrpc;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadFactory;

public class GrpcServer {
  private static final Logger logger = LogManager.getLogger(GrpcServer.class);

  public static void main(String... args) throws Exception {
    logger.info("starting server");

    String host = System.getProperty("host", "0.0.0.0");
    int port = Integer.getInteger("port", 8001);
    boolean useEpoll = Boolean.getBoolean("usePoll");
  
    Class channel;
    
    if (useEpoll) {
      channel = EpollServerSocketChannel.class;
    } else  {
      channel = NioServerSocketChannel.class;
    }

    ThreadFactory tf = new DefaultThreadFactory("server-elg-", true /*daemon */);
    NioEventLoopGroup boss = new NioEventLoopGroup(1, tf);
    NioEventLoopGroup worker = new NioEventLoopGroup(0, tf);
    NettyServerBuilder builder =
        NettyServerBuilder.forPort(port)
            .bossEventLoopGroup(boss)
            .workerEventLoopGroup(worker)
            .channelType(channel)
            .addService(new DefaultService())
            .directExecutor()
            .maxConcurrentCallsPerConnection(Runtime.getRuntime().availableProcessors() * 256)
            .flowControlWindow(NettyChannelBuilder.DEFAULT_FLOW_CONTROL_WINDOW * 10);

    io.grpc.Server start = builder.build();
    start.start();

    logger.info("server started");
    start.awaitTermination();
  }

  static class DefaultService extends SimpleServiceGrpc.SimpleServiceImplBase {
    @Override
    public void requestReply(
        SimpleRequest request, StreamObserver<SimpleResponse> responseObserver) {
      SimpleResponse response =
          SimpleResponse.newBuilder().setResponseMessage(request.getRequestMessage()).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
  }
}
