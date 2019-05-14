package io.netifi.thunderdome.rsocketrpc;

import com.google.protobuf.Empty;
import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleResponse;
import io.netifi.testing.protobuf.SimpleService;
import io.netifi.testing.protobuf.SimpleServiceServer;
import io.rsocket.Frame;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class RSocketRpcServer {
  private static final Logger logger = LogManager.getLogger(RSocketRpcServer.class);

  public static void main(String... args) {
    String host = System.getProperty("host", "127.0.0.1");
    int port = Integer.getInteger("port", 8001);
    RSocketFactory.receive()
        .frameDecoder(Frame::retain)
        .acceptor(
            (setup, sendingSocket) ->
                Mono.just(
                    new SimpleServiceServer(
                        new DefaultService(), Optional.empty(), Optional.empty())))
        .transport(TcpServerTransport.create(host, port))
        .start()
        .block()
        .onClose()
        .doOnSubscribe(s -> logger.info("server started"))
        .block();
  }

  static class DefaultService implements SimpleService {
    @Override
    public Mono<SimpleResponse> requestReply(SimpleRequest message, io.netty.buffer.ByteBuf metadata) {
      return Mono.create(
          sink -> {
            String requestMessage = message.getRequestMessage();
            SimpleResponse response =
                SimpleResponse.newBuilder().setResponseMessage(requestMessage).build();
            sink.success(response);
          });
    }

    @Override
    public Mono<Empty> fireAndForget(SimpleRequest message, io.netty.buffer.ByteBuf metadata) {
      return notImplementedMono();
    }

    @Override
    public Flux<SimpleResponse> requestStream(SimpleRequest message, io.netty.buffer.ByteBuf metadata) {
      return notImplementedFlux();
    }

    @Override
    public Mono<SimpleResponse> streamingRequestSingleResponse(Publisher<SimpleRequest> messages, io.netty.buffer.ByteBuf metadata) {
      return notImplementedMono();
    }

    @Override
    public Flux<SimpleResponse> streamingRequestAndResponse(Publisher<SimpleRequest> messages, io.netty.buffer.ByteBuf metadata) {
      return notImplementedFlux();
    }

    private static <T> Mono<T> notImplementedMono() {
      return Mono.error(new UnsupportedOperationException("Not implemented"));
    }

    private static <T> Flux<T> notImplementedFlux() {
      return Flux.error(new UnsupportedOperationException("Not implemented"));
    }
  }
}
