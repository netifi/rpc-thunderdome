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

import java.time.Duration;

public class RSocketRpcRequestReplyClient {
  private static final Logger logger = LogManager.getLogger(RSocketRpcRequestReplyClient.class);

  public static void main(String... args) {

    String host = System.getProperty("host", "127.0.0.1");
    int port = Integer.getInteger("port", 8001);
    int concurrency = Integer.getInteger("concurrency", 64);

    int warmup = 1_000_000;
    int count = 1_000_000;

    RSocket rSocket =
        RSocketFactory.connect()
            .frameDecoder(Frame::retain)
            .keepAlive(Duration.ofSeconds(1), Duration.ofSeconds(5), 1)
            .transport(TcpClientTransport.create(host, port))
            .start()
            .block();

    SimpleServiceClient client = new SimpleServiceClient(rSocket);

    logger.info("starting warmup...");
    Flux.range(0, warmup)
        .flatMap(
            integer -> {
              SimpleRequest request = SimpleRequest.newBuilder().setRequestMessage("hello").build();
              return client.requestReply(request);
            })
        .doOnError(Throwable::printStackTrace)
        .blockLast();
    logger.info("warmup complete");

    long start = System.nanoTime();
    Histogram histogram = new Histogram(3600000000000L, 3);
    logger.info("starting test - sending " + count);
    Flux.range(0, count)
        .flatMap(
            integer -> {
              long s = System.nanoTime();
              SimpleRequest request = SimpleRequest.newBuilder().setRequestMessage("hello").build();

              return client
                  .requestReply(request)
                  .doFinally(
                      simpleResponse -> {
                        histogram.recordValue(System.nanoTime() - s);
                      });
            },
            concurrency)
        .blockLast();
    histogram.outputPercentileDistribution(System.out, 1000.0d);
    double completedMillis = (System.nanoTime() - start) / 1_000_000d;
    double rps = count / ((System.nanoTime() - start) / 1_000_000_000d);
    logger.info("test complete in {} ms", completedMillis);
    logger.info("test rps {}", rps);
  }
}
