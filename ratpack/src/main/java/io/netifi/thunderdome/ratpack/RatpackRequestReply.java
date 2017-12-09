package io.netifi.thunderdome.ratpack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.HdrHistogram.Histogram;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.client.HttpClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RatpackRequestReply {

  public static void main(String... args) throws Exception {
    int warmup = 1_000_000;

    int count = Integer.getInteger("count", 1_000_000);
    int concurrency = Integer.getInteger("concurrency", 16);

    String host = System.getProperty("host", "127.0.0.1");
    int port = Integer.getInteger("port", 5050);

    ObjectMapper mapper = new ObjectMapper();

    HttpClient client = HttpClient.create(host, port);

    System.out.println("starting warmup...");
    Flux.range(0, warmup)
        .flatMap(
            integer -> {
              return client
                  .post(
                      "/",
                      req -> {
                        SimpleRequest request = new SimpleRequest();
                        request.setMessage("hello");
                        byte[] bytes = new byte[0];
                        try {
                          bytes = mapper.writeValueAsBytes(request);
                        } catch (JsonProcessingException e) {
                          e.printStackTrace();
                        }
                        return req.send(Mono.just(Unpooled.wrappedBuffer(bytes)));
                      })
                  .flatMap(
                      res -> {
                        StringBuilder builder = new StringBuilder();
                        return res.receiveContent()
                            .doOnNext(
                                httpContent -> {
                                  ByteBuf content = httpContent.content();
                                  CharSequence charSequence =
                                      content.readCharSequence(
                                          content.readableBytes(), StandardCharsets.UTF_8);
                                  builder.append(charSequence);
                                })
                            .then(
                                Mono.fromRunnable(
                                    () -> {
                                      try {
                                        mapper.readValue(builder.toString(), SimpleResponse.class);
                                      } catch (IOException e) {
                                        System.out.println(builder);
                                        throw new RuntimeException(e);
                                      }
                                    }));
                      });
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
              return client
                  .post(
                      "/",
                      req -> {
                        SimpleRequest request = new SimpleRequest();
                        request.setMessage("hello");
                        byte[] bytes = new byte[0];
                        try {
                          bytes = mapper.writeValueAsBytes(request);
                        } catch (JsonProcessingException e) {
                          e.printStackTrace();
                        }
                        return req.send(Mono.just(Unpooled.wrappedBuffer(bytes)));
                      })
                  .flatMap(
                      res -> {
                        StringBuilder builder = new StringBuilder();
                        return res.receiveContent()
                            .doOnNext(
                                httpContent -> {
                                  ByteBuf content = httpContent.content();
                                  CharSequence charSequence =
                                      content.readCharSequence(
                                          content.readableBytes(), StandardCharsets.UTF_8);
                                  builder.append(charSequence);
                                })
                            .then(
                                Mono.fromRunnable(
                                    () -> {
                                      try {
                                        mapper.readValue(builder.toString(), SimpleResponse.class);
                                      } catch (IOException e) {
                                        System.out.println(builder);
                                        throw new RuntimeException(e);
                                      }
                                      histogram.recordValue(System.nanoTime() - s);
                                    }));
                      });
            },
            concurrency)
        .blockLast();
    histogram.outputPercentileDistribution(System.out, 1000.0d);
    double completedMillis = (System.nanoTime() - start) / 1_000_000d;
    double rps = count / ((System.nanoTime() - start) / 1_000_000_000d);
    System.out.println("test complete in " + completedMillis + "ms");
    System.out.println("test rps " + rps);
    System.out.println();
  }
}
