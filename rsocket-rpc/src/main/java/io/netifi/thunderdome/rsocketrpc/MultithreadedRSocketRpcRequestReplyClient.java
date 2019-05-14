package io.netifi.thunderdome.rsocketrpc;

import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleServiceClient;
import io.rsocket.Frame;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.HdrHistogram.Histogram;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class MultithreadedRSocketRpcRequestReplyClient {
  private static final Logger logger =
      LogManager.getLogger(MultithreadedRSocketRpcRequestReplyClient.class);

  public static void main(String... args) {

    String host = System.getProperty("host", "127.0.0.1");
    int port = Integer.getInteger("port", 8001);
    int count = Integer.getInteger("count", 1_000_000);
    int concurrency = Integer.getInteger("concurrency", 16);
    int threads = Integer.getInteger("threads", 3);

    Histogram histogram = new Histogram(3600000000000L, 3);

    logger.info("starting test - sending {}", count);
    CountDownLatch latch = new CountDownLatch(threads);
    long start = System.nanoTime();
    Flux.range(1, threads)
        .flatMap(
            i -> {
              return Mono.fromRunnable(
                      () -> {
                        logger.info("start thread -> {}", i);
                        RSocket rSocket =
                            RSocketFactory.connect()
                                .frameDecoder(Frame::retain)
                                .keepAlive(Duration.ofSeconds(1), Duration.ofSeconds(5), 1)
                                .transport(TcpClientTransport.create(host, port))
                                .start()
                                .block();

                        SimpleServiceClient client = new SimpleServiceClient(rSocket);
                        int i1 = count / threads;
                        try {
                          latch.countDown();
                          latch.await();
                        } catch (InterruptedException e) {
                          throw new RuntimeException(e);
                        }
                        Flux.range(0, i1)
                            .flatMap(
                                integer -> {
                                  long s = System.nanoTime();
                                  SimpleRequest request =
                                      SimpleRequest.newBuilder().setRequestMessage("hello").build();

                                  return client
                                      .requestReply(request)
                                      .doFinally(
                                          simpleResponse -> {
                                            histogram.recordValue(System.nanoTime() - s);
                                          });
                                },
                                concurrency)
                            .blockLast();
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
}
