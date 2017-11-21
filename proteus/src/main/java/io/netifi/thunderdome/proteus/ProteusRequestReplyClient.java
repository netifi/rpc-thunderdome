package io.netifi.thunderdome.proteus;

import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleServiceClient;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.HdrHistogram.Histogram;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ProteusRequestReplyClient {
  public static void main(String... args) {
    int warmup = 1_000_000;
    int count = 1_000_000;
    
      RSocket rSocket =
          RSocketFactory.connect()
              .keepAlive(Duration.ofSeconds(1), Duration.ofSeconds(5), 1)
              .transport(TcpClientTransport.create("127.0.0.1", 8080))
              .start()
              .block();
    
      SimpleServiceClient client = new SimpleServiceClient(rSocket);
    
      System.out.println("starting warmup...");
    Flux.range(0, warmup)
        .flatMap(
            integer -> {
              SimpleRequest request = SimpleRequest.newBuilder().setRequestMessage("hello").build();
              return client.requestReply(request);
            })
        .doOnError(Throwable::printStackTrace)
        .blockLast();
    System.out.println("warmup complete");

    long start = System.nanoTime();
    Histogram histogram = new Histogram(3600000000000L, 3);
    System.out.println("starting test - sending " + count);
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
            }, 16)
        .blockLast();
    histogram.outputPercentileDistribution(System.out, 1000.0d);
    double completedMillis = (System.nanoTime() - start) / 1_000_000d;
    double rps = count / ((System.nanoTime() - start) / 1_000_000_000d);
    System.out.println("test complete in " + completedMillis + "ms");
    System.out.println("test rps " + rps);
    System.out.println();
  }
}
