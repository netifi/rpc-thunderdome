package io.netifi.thunderdome.grpc.nonblocking.rs;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleResponse;
import io.netifi.testing.protobuf.SimpleServiceGrpc;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.HdrHistogram.Histogram;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

public class MultithreadedGrpcRequestReplyClient {
  private static final Logger logger = LogManager.getLogger(MultithreadedGrpcRequestReplyClient.class);

  public static void main(String... args) throws Exception {
    String host = System.getProperty("host", "127.0.0.1");
    int port = Integer.getInteger("port", 8001);
    int count = Integer.getInteger("count", 1_000_000);
    int concurrency = Integer.getInteger("concurrency", 64);
    int threads = Integer.getInteger("threads", 3);
    boolean useEpoll = Boolean.getBoolean("usePoll");

    ThreadFactory tf = new DefaultThreadFactory("client-elg-", true /*daemon */);
    NioEventLoopGroup worker = new NioEventLoopGroup(0, tf);

    Class channel;

    if (useEpoll) {
      channel = EpollSocketChannel.class;
    } else {
      channel = NioSocketChannel.class;
    }

    Histogram histogram = new Histogram(3600000000000L, 3);
    logger.info("starting test - sending {}", count);
    CountDownLatch latch = new CountDownLatch(threads);
    long start = System.nanoTime();
    Flux.range(1, threads)
        .flatMap(
            i -> {
              logger.info("start thread -> {}", i);
              ManagedChannel managedChannel =
                  NettyChannelBuilder.forAddress(host, port)
                      .eventLoopGroup(worker)
                      .channelType(channel)
                      .directExecutor()
                      .flowControlWindow(NettyChannelBuilder.DEFAULT_FLOW_CONTROL_WINDOW)
                      .usePlaintext()
                      .build();

              SimpleServiceGrpc.SimpleServiceStub simpleService =
                  SimpleServiceGrpc.newStub(managedChannel);

              return Mono.fromRunnable(
                      () -> {
                        int i1 = count / threads;
                        try {
                          latch.countDown();
                          latch.await();
                        } catch (InterruptedException e) {
                          throw new RuntimeException(e);
                        }
                        execute(i1, simpleService, histogram, concurrency);
                      })
                  .subscribeOn(Schedulers.elastic());
            })
        .blockLast();

    histogram.outputPercentileDistribution(System.out, 1000.0d);
    double completedMillis = (System.nanoTime() - start) / 1_000_000d;
    double rps = count / ((System.nanoTime() - start) / 1_000_000_000d);
    logger.info("test complete in {} ms", completedMillis);
    logger.info("test rps {}", rps);
  }

  private static void execute(
      int count,
      SimpleServiceGrpc.SimpleServiceStub simpleService,
      Histogram histogram,
      int concurrency) {
    Flux.range(1, count)
        .flatMap(
            integer -> {
              long s = System.nanoTime();
              SimpleRequest request = SimpleRequest.newBuilder().setRequestMessage("hello").build();
              return Flux.create(
                  sink -> {
                    simpleService.requestReply(
                        request,
                        new StreamObserver<SimpleResponse>() {
                          @Override
                          public void onNext(SimpleResponse value) {
                            sink.next(value);
                          }

                          @Override
                          public void onError(Throwable t) {
                            sink.error(t);
                          }

                          @Override
                          public void onCompleted() {
                            histogram.recordValue(System.nanoTime() - s);
                            sink.complete();
                          }
                        });
                  });
            },
            concurrency)
        .blockLast();
  }
}
