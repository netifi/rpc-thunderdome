package io.netifi.thunderdome.proteus;

import io.netifi.proteus.AbstractProteusService;
import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleResponse;
import io.netifi.testing.protobuf.SimpleService;
import io.netifi.testing.protobuf.SimpleServiceServer;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProteusServer {
  public static void main(String... args) {
    System.out.println("starting server");
    RSocketFactory.receive()
        .acceptor(
            (setup, sendingSocket) -> {
              return Mono.just(new SimpleServiceServer(new DefaultService()));
            })
        .transport(TcpServerTransport.create("127.0.0.1", 8080))
        .start()
        .block()
        .onClose()
        .doOnSubscribe(s -> System.out.println("server started"))
        .block();
  }

  static class DefaultService extends AbstractProteusService implements SimpleService {
    @Override
    public Mono<SimpleResponse> requestReply(SimpleRequest message) {
      return Mono.create(
          sink -> {
            String requestMessage = message.getRequestMessage();
            SimpleResponse response =
                SimpleResponse.newBuilder().setResponseMessage(requestMessage).build();
            sink.success(response);
          });
    }

    @Override
    public Mono<Void> fireAndForget(SimpleRequest message) {
      return null;
    }

    @Override
    public Flux<SimpleResponse> requestStream(SimpleRequest message) {
      return null;
    }

    @Override
    public Mono<SimpleResponse> streamingRequestSingleResponse(Publisher<SimpleRequest> messages) {
      return null;
    }

    @Override
    public Flux<SimpleResponse> streamingRequestAndResponse(Publisher<SimpleRequest> messages) {
      return null;
    }
  }
}
