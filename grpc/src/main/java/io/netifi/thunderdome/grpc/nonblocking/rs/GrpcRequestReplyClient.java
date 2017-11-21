package io.netifi.thunderdome.grpc.nonblocking.rs;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleResponse;
import io.netifi.testing.protobuf.SimpleServiceGrpc;
import org.HdrHistogram.Histogram;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class GrpcRequestReplyClient {
  public static void main(String... args) throws Exception {
    int warmup = 1_000_000;
    int count = 1_000_000;
  
    ExecutorService executorService = Executors.newCachedThreadPool();
    ManagedChannel managedChannel =
        ManagedChannelBuilder
            .forAddress("127.0.0.1", 8080)
            .usePlaintext(true)
            .directExecutor()
            .build();
    SimpleServiceGrpc.SimpleServiceFutureStub simpleService =
        SimpleServiceGrpc.newFutureStub(managedChannel);

    CountDownLatch warmupLatch = new CountDownLatch(warmup);
    
    System.out.println("starting warmup...");

    for (int i = 0; i < warmup; i++) {
      SimpleRequest hello = SimpleRequest.newBuilder().setRequestMessage("hello").build();
      simpleService.requestReply(hello).addListener(warmupLatch::countDown, executorService);
    }

    warmupLatch.await();
    System.out.println("warmup complete");

    System.out.println("starting test - sending " + count);
    Histogram histogram = new Histogram(3600000000000L, 3);
    long start = System.nanoTime();
    Flux.range(1, count)
        .flatMap(
            integer -> {
              long s = System.nanoTime();
              SimpleRequest request = SimpleRequest.newBuilder().setRequestMessage("hello").build();
              return Mono.create(
                      sink -> {
                        ListenableFuture<SimpleResponse> future =
                            simpleService.requestReply(request);
                        sink.onDispose(() -> future.cancel(true));
                        future.addListener(
                            () -> {
                              try {
                                sink.success(future.get());
                              } catch (Throwable t) {
                                sink.error(t);
                              }
                            },
                            executorService);
                      })
                  .doFinally(
                      simpleResponse -> {
                        histogram.recordValue(System.nanoTime() - s);
                      });
            }, 64)
        .blockLast();
    histogram.outputPercentileDistribution(System.out, 1000.0d);
    double completedMillis = (System.nanoTime() - start) / 1_000_000d;
    double rps = count / ((System.nanoTime() - start) / 1_000_000_000d);
    System.out.println("test complete in " + completedMillis + "ms");
    System.out.println("test rps " + rps);
    System.out.println();
    System.exit(0);
  }
}
