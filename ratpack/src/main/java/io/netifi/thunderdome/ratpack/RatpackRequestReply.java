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
    int count = 1_000_000;

    ObjectMapper mapper = new ObjectMapper();

    HttpClient client = HttpClient.create("127.0.0.1", 5050);

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
            })
        .blockLast();
    histogram.outputPercentileDistribution(System.out, 1000.0d);
    double completedMillis = (System.nanoTime() - start) / 1_000_000d;
    double rps = count / ((System.nanoTime() - start) / 1_000_000_000d);
    System.out.println("test complete in " + completedMillis + "ms");
    System.out.println("test rps " + rps);
    System.out.println();
  }
}

/*
 EmbeddedApp.fromHandlers(chain -> {
       chain
         .get("simpleGet", ctx -> {
           PublicAddress address = ctx.get(PublicAddress.class);         //find local ip address
           HttpClient httpClient = ctx.get(HttpClient.class);            //get httpClient
           URI uri = address.get("httpClientGet");

           httpClient.get(uri).then(response ->
               ctx.render(response.getBody().getText())  //Render the response from the httpClient GET request
           );
         })
         .get("simplePost", ctx -> {
           PublicAddress address = ctx.get(PublicAddress.class);  //find local ip address
           HttpClient httpClient = ctx.get(HttpClient.class);     //get httpClient
           URI uri = address.get("httpClientPost");

           httpClient.post(uri, s -> s.getBody().text("foo")).then(response ->
             ctx.render(response.getBody().getText())   //Render the response from the httpClient POST request
           );
         })
         .get("httpClientGet", ctx -> ctx.render("httpClientGet"))
         .post("httpClientPost", ctx -> ctx.render(ctx.getRequest().getBody().map(b -> b.getText().toUpperCase())));
     }
   ).test(testHttpClient -> {
     assertEquals("httpClientGet", testHttpClient.getText("/simpleGet"));
     assertEquals("FOO", testHttpClient.getText("/simplePost"));
   });
*/
