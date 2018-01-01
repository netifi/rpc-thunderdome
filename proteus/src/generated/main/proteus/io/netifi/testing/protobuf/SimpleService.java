package io.netifi.testing.protobuf;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.4.0)",
    comments = "Source: io/netifi/thunderdome/proteus/simpleservice.proto")
public interface SimpleService {
  int NAMESPACE_ID = 298608432;
  int SERVICE_ID = -1305494814;
  int METHOD_REQUEST_REPLY = 2003126180;
  int METHOD_FIRE_AND_FORGET = 238626589;
  int METHOD_REQUEST_STREAM = 2131592896;
  int METHOD_STREAMING_REQUEST_SINGLE_RESPONSE = -1288641191;
  int METHOD_STREAMING_REQUEST_AND_RESPONSE = -871337420;

  /**
   * <pre>
   * Request / Response
   * </pre>
   */
  reactor.core.publisher.Mono<io.netifi.testing.protobuf.SimpleResponse> requestReply(io.netifi.testing.protobuf.SimpleRequest message, io.netty.buffer.ByteBuf metadata);

  /**
   * <pre>
   * Fire-and-Forget
   * </pre>
   */
  reactor.core.publisher.Mono<Void> fireAndForget(io.netifi.testing.protobuf.SimpleRequest message, io.netty.buffer.ByteBuf metadata);

  /**
   * <pre>
   * Single Request / Streaming Response
   * </pre>
   */
  reactor.core.publisher.Flux<io.netifi.testing.protobuf.SimpleResponse> requestStream(io.netifi.testing.protobuf.SimpleRequest message, io.netty.buffer.ByteBuf metadata);

  /**
   * <pre>
   * Streaming Request / Single Response
   * </pre>
   */
  reactor.core.publisher.Mono<io.netifi.testing.protobuf.SimpleResponse> streamingRequestSingleResponse(org.reactivestreams.Publisher<io.netifi.testing.protobuf.SimpleRequest> messages, io.netty.buffer.ByteBuf metadata);

  /**
   * <pre>
   * Streaming Request / Streaming Response
   * </pre>
   */
  reactor.core.publisher.Flux<io.netifi.testing.protobuf.SimpleResponse> streamingRequestAndResponse(org.reactivestreams.Publisher<io.netifi.testing.protobuf.SimpleRequest> messages, io.netty.buffer.ByteBuf metadata);
}
