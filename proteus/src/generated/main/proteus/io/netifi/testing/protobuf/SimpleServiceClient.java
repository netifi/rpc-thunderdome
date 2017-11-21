package io.netifi.testing.protobuf;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.2.4)",
    comments = "Source: io/netifi/thunderdome/proteus/simpleservice.proto")
public final class SimpleServiceClient implements SimpleService {
  private final io.rsocket.RSocket rSocket;

  public SimpleServiceClient(io.rsocket.RSocket rSocket) {
    this.rSocket = rSocket;
  }

  @java.lang.Override
  public reactor.core.publisher.Mono<io.netifi.testing.protobuf.SimpleResponse> requestReply(io.netifi.testing.protobuf.SimpleRequest message) {
    final int length = io.netifi.proteus.frames.ProteusMetadata.computeLength();
    io.netty.buffer.ByteBuf metadata = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(length);
    io.netifi.proteus.frames.ProteusMetadata.encode(metadata, SimpleService.NAMESPACE_ID, SimpleService.SERVICE_ID, SimpleService.METHOD_REQUEST_REPLY);
    io.netty.buffer.ByteBuf data = serialize(message);

    return rSocket.requestResponse(io.rsocket.util.ByteBufPayload.create(data, metadata))
      .map(deserializer(io.netifi.testing.protobuf.SimpleResponse.parser()));
  }

  @java.lang.Override
  public reactor.core.publisher.Mono<Void> fireAndForget(io.netifi.testing.protobuf.SimpleRequest message) {
    final int length = io.netifi.proteus.frames.ProteusMetadata.computeLength();
    io.netty.buffer.ByteBuf metadata = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(length);
    io.netifi.proteus.frames.ProteusMetadata.encode(metadata, SimpleService.NAMESPACE_ID, SimpleService.SERVICE_ID, SimpleService.METHOD_FIRE_AND_FORGET);
    io.netty.buffer.ByteBuf data = serialize(message);

    return rSocket.fireAndForget(io.rsocket.util.ByteBufPayload.create(data, metadata));
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<io.netifi.testing.protobuf.SimpleResponse> requestStream(io.netifi.testing.protobuf.SimpleRequest message) {
    final int length = io.netifi.proteus.frames.ProteusMetadata.computeLength();
    io.netty.buffer.ByteBuf metadata = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(length);
    io.netifi.proteus.frames.ProteusMetadata.encode(metadata, SimpleService.NAMESPACE_ID, SimpleService.SERVICE_ID, SimpleService.METHOD_REQUEST_STREAM);
    io.netty.buffer.ByteBuf data = serialize(message);

    return rSocket.requestStream(io.rsocket.util.ByteBufPayload.create(data, metadata))
      .map(deserializer(io.netifi.testing.protobuf.SimpleResponse.parser()));
  }

  @java.lang.Override
  public reactor.core.publisher.Mono<io.netifi.testing.protobuf.SimpleResponse> streamingRequestSingleResponse(org.reactivestreams.Publisher<io.netifi.testing.protobuf.SimpleRequest> messages) {
    return rSocket.requestChannel(reactor.core.publisher.Flux.from(messages).map(
      new java.util.function.Function<com.google.protobuf.MessageLite, io.rsocket.Payload>() {
        boolean once;

        @java.lang.Override
        public io.rsocket.Payload apply(com.google.protobuf.MessageLite message) {
          io.netty.buffer.ByteBuf data = serialize(message);
          if (!once) {
            final int length = io.netifi.proteus.frames.ProteusMetadata.computeLength();
            final io.netty.buffer.ByteBuf metadata = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(length);
            io.netifi.proteus.frames.ProteusMetadata.encode(metadata, SimpleService.NAMESPACE_ID, SimpleService.SERVICE_ID, SimpleService.METHOD_STREAMING_REQUEST_SINGLE_RESPONSE);
            return io.rsocket.util.ByteBufPayload.create(data, metadata);
          } else {
            return io.rsocket.util.ByteBufPayload.create(data);
          }
        }
      })).map(deserializer(io.netifi.testing.protobuf.SimpleResponse.parser())).single();
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<io.netifi.testing.protobuf.SimpleResponse> streamingRequestAndResponse(org.reactivestreams.Publisher<io.netifi.testing.protobuf.SimpleRequest> messages) {
    return rSocket.requestChannel(reactor.core.publisher.Flux.from(messages).map(
      new java.util.function.Function<com.google.protobuf.MessageLite, io.rsocket.Payload>() {
        boolean once;

        @java.lang.Override
        public io.rsocket.Payload apply(com.google.protobuf.MessageLite message) {
          io.netty.buffer.ByteBuf data = serialize(message);
          if (!once) {
            final int length = io.netifi.proteus.frames.ProteusMetadata.computeLength();
            final io.netty.buffer.ByteBuf metadata = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(length);
            io.netifi.proteus.frames.ProteusMetadata.encode(metadata, SimpleService.NAMESPACE_ID, SimpleService.SERVICE_ID, SimpleService.METHOD_STREAMING_REQUEST_AND_RESPONSE);
            return io.rsocket.util.ByteBufPayload.create(data, metadata);
          } else {
            return io.rsocket.util.ByteBufPayload.create(data);
          }
        }
      })).map(deserializer(io.netifi.testing.protobuf.SimpleResponse.parser()));
  }

  private static io.netty.buffer.ByteBuf serialize(final com.google.protobuf.MessageLite message) {
    io.netty.buffer.ByteBuf byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(message.getSerializedSize());
    try {
      message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.nioBuffer(0, byteBuf.writableBytes())));
      byteBuf.writerIndex(byteBuf.capacity());
      return byteBuf;
    } catch (Throwable t) {
      byteBuf.release();
      throw new RuntimeException(t);
    }
  }

  private static <T> java.util.function.Function<io.rsocket.Payload, T> deserializer(final com.google.protobuf.Parser<T> parser) {
    return new java.util.function.Function<io.rsocket.Payload, T>() {
      @java.lang.Override
      public T apply(io.rsocket.Payload payload) {
        try {
          com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(payload.getData());
          return parser.parseFrom(is);
        } catch (Throwable t) {
          throw new RuntimeException(t);
        } finally {
          payload.release();
        }
      }
    };
  }
}
