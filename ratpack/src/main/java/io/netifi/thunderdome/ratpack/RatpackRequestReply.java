package io.netifi.thunderdome.ratpack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.HdrHistogram.Histogram;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.io.IOException;
import java.io.InputStream;

public class RatpackRequestReply {
  private static final Logger logger = LogManager.getLogger(RatpackRequestReply.class);

  public static void main(String... args) {
    int warmup = 1_000_000;

    int count = Integer.getInteger("count", 1_000_000);
    int concurrency = Integer.getInteger("concurrency", 16);

    String host = System.getProperty("host", "127.0.0.1");
    int port = Integer.getInteger("port", 5050);

    ObjectMapper mapper = new ObjectMapper();

    ConnectionProvider connectionProvider = ConnectionProvider
        .fixed("test", concurrency);
    HttpClient client = HttpClient
        .create(connectionProvider)
        .tcpConfiguration(c -> c.port(port).host(host));

    logger.info("starting warmup...");

    Flux.range(0, warmup)
        .flatMap(
            integer -> client
                .post()
                .uri("/")
                .send(newRequest(mapper))
                .responseContent()
                .aggregate()
                .asInputStream()
                .map(content -> toResponse(mapper, content)))
        .doOnError(Throwable::printStackTrace)
        .blockLast();
    logger.info("warmup complete");

    long start = System.nanoTime();
    Histogram histogram = new Histogram(3600000000000L, 3);
    logger.info("starting test - sending {}", count);
    Flux.range(0, count)
        .flatMap(
            integer -> {
              long s = System.nanoTime();
              return client
                  .post()
                  .uri("/")
                  .send(newRequest(mapper))
                  .responseContent()
                  .aggregate()
                  .asInputStream()
                  .map(content -> toResponse(mapper, content))
                  .doOnNext(r -> histogram.recordValue(System.nanoTime() - s));
            },
            concurrency)
        .doFinally(s -> connectionProvider.dispose())
        .blockLast();
    histogram.outputPercentileDistribution(System.out, 1000.0d);
    double completedMillis = (System.nanoTime() - start) / 1_000_000d;
    double rps = count / ((System.nanoTime() - start) / 1_000_000_000d);
    logger.info("test complete in {} ms", completedMillis);
    logger.info("test rps {}", rps);
  }

  private static SimpleResponse toResponse(ObjectMapper mapper, InputStream response) {
    try {
      return mapper.readValue(response, SimpleResponse.class);
    } catch (IOException e) {
      logger.error(response, e);
      throw new RuntimeException(e);
    }
  }

  private static Mono<ByteBuf> newRequest(ObjectMapper mapper) {
    SimpleRequest request = new SimpleRequest();
    request.setMessage("hello");
    try {
      return Mono.just(Unpooled.wrappedBuffer(mapper.writeValueAsBytes(request)));
    } catch (JsonProcessingException e) {
      return Mono.error(e);
    }
  }
}
