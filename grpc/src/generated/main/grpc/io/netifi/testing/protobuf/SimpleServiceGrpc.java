package io.netifi.testing.protobuf;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: io/netifi/thunderdome/grpc/nonblocking/stock/simpleservice.proto")
public final class SimpleServiceGrpc {

  private SimpleServiceGrpc() {}

  public static final String SERVICE_NAME = "io.netifi.testing.SimpleService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.netifi.testing.protobuf.SimpleRequest,
      io.netifi.testing.protobuf.SimpleResponse> METHOD_REQUEST_REPLY =
      io.grpc.MethodDescriptor.<io.netifi.testing.protobuf.SimpleRequest, io.netifi.testing.protobuf.SimpleResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "io.netifi.testing.SimpleService", "RequestReply"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleResponse.getDefaultInstance()))
          .setSchemaDescriptor(new SimpleServiceMethodDescriptorSupplier("RequestReply"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.netifi.testing.protobuf.SimpleRequest,
      com.google.protobuf.Empty> METHOD_FIRE_AND_FORGET =
      io.grpc.MethodDescriptor.<io.netifi.testing.protobuf.SimpleRequest, com.google.protobuf.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "io.netifi.testing.SimpleService", "FireAndForget"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.google.protobuf.Empty.getDefaultInstance()))
          .setSchemaDescriptor(new SimpleServiceMethodDescriptorSupplier("FireAndForget"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.netifi.testing.protobuf.SimpleRequest,
      io.netifi.testing.protobuf.SimpleResponse> METHOD_REQUEST_STREAM =
      io.grpc.MethodDescriptor.<io.netifi.testing.protobuf.SimpleRequest, io.netifi.testing.protobuf.SimpleResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "io.netifi.testing.SimpleService", "RequestStream"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleResponse.getDefaultInstance()))
          .setSchemaDescriptor(new SimpleServiceMethodDescriptorSupplier("RequestStream"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.netifi.testing.protobuf.SimpleRequest,
      io.netifi.testing.protobuf.SimpleResponse> METHOD_STREAMING_REQUEST_SINGLE_RESPONSE =
      io.grpc.MethodDescriptor.<io.netifi.testing.protobuf.SimpleRequest, io.netifi.testing.protobuf.SimpleResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "io.netifi.testing.SimpleService", "StreamingRequestSingleResponse"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleResponse.getDefaultInstance()))
          .setSchemaDescriptor(new SimpleServiceMethodDescriptorSupplier("StreamingRequestSingleResponse"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.netifi.testing.protobuf.SimpleRequest,
      io.netifi.testing.protobuf.SimpleResponse> METHOD_STREAMING_REQUEST_AND_RESPONSE =
      io.grpc.MethodDescriptor.<io.netifi.testing.protobuf.SimpleRequest, io.netifi.testing.protobuf.SimpleResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "io.netifi.testing.SimpleService", "StreamingRequestAndResponse"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              io.netifi.testing.protobuf.SimpleResponse.getDefaultInstance()))
          .setSchemaDescriptor(new SimpleServiceMethodDescriptorSupplier("StreamingRequestAndResponse"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SimpleServiceStub newStub(io.grpc.Channel channel) {
    return new SimpleServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SimpleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SimpleServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SimpleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SimpleServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SimpleServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Request / Response
     * </pre>
     */
    public void requestReply(io.netifi.testing.protobuf.SimpleRequest request,
        io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REQUEST_REPLY, responseObserver);
    }

    /**
     * <pre>
     * Fire-and-Forget
     * </pre>
     */
    public void fireAndForget(io.netifi.testing.protobuf.SimpleRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_FIRE_AND_FORGET, responseObserver);
    }

    /**
     * <pre>
     * Single Request / Streaming Response
     * </pre>
     */
    public void requestStream(io.netifi.testing.protobuf.SimpleRequest request,
        io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REQUEST_STREAM, responseObserver);
    }

    /**
     * <pre>
     * Streaming Request / Single Response
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleRequest> streamingRequestSingleResponse(
        io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_STREAMING_REQUEST_SINGLE_RESPONSE, responseObserver);
    }

    /**
     * <pre>
     * Streaming Request / Streaming Response
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleRequest> streamingRequestAndResponse(
        io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_STREAMING_REQUEST_AND_RESPONSE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_REQUEST_REPLY,
            asyncUnaryCall(
              new MethodHandlers<
                io.netifi.testing.protobuf.SimpleRequest,
                io.netifi.testing.protobuf.SimpleResponse>(
                  this, METHODID_REQUEST_REPLY)))
          .addMethod(
            METHOD_FIRE_AND_FORGET,
            asyncUnaryCall(
              new MethodHandlers<
                io.netifi.testing.protobuf.SimpleRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_FIRE_AND_FORGET)))
          .addMethod(
            METHOD_REQUEST_STREAM,
            asyncServerStreamingCall(
              new MethodHandlers<
                io.netifi.testing.protobuf.SimpleRequest,
                io.netifi.testing.protobuf.SimpleResponse>(
                  this, METHODID_REQUEST_STREAM)))
          .addMethod(
            METHOD_STREAMING_REQUEST_SINGLE_RESPONSE,
            asyncClientStreamingCall(
              new MethodHandlers<
                io.netifi.testing.protobuf.SimpleRequest,
                io.netifi.testing.protobuf.SimpleResponse>(
                  this, METHODID_STREAMING_REQUEST_SINGLE_RESPONSE)))
          .addMethod(
            METHOD_STREAMING_REQUEST_AND_RESPONSE,
            asyncBidiStreamingCall(
              new MethodHandlers<
                io.netifi.testing.protobuf.SimpleRequest,
                io.netifi.testing.protobuf.SimpleResponse>(
                  this, METHODID_STREAMING_REQUEST_AND_RESPONSE)))
          .build();
    }
  }

  /**
   */
  public static final class SimpleServiceStub extends io.grpc.stub.AbstractStub<SimpleServiceStub> {
    private SimpleServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SimpleServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SimpleServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SimpleServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Request / Response
     * </pre>
     */
    public void requestReply(io.netifi.testing.protobuf.SimpleRequest request,
        io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REQUEST_REPLY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Fire-and-Forget
     * </pre>
     */
    public void fireAndForget(io.netifi.testing.protobuf.SimpleRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_FIRE_AND_FORGET, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Single Request / Streaming Response
     * </pre>
     */
    public void requestStream(io.netifi.testing.protobuf.SimpleRequest request,
        io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_REQUEST_STREAM, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streaming Request / Single Response
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleRequest> streamingRequestSingleResponse(
        io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_STREAMING_REQUEST_SINGLE_RESPONSE, getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Streaming Request / Streaming Response
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleRequest> streamingRequestAndResponse(
        io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_STREAMING_REQUEST_AND_RESPONSE, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class SimpleServiceBlockingStub extends io.grpc.stub.AbstractStub<SimpleServiceBlockingStub> {
    private SimpleServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SimpleServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SimpleServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SimpleServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Request / Response
     * </pre>
     */
    public io.netifi.testing.protobuf.SimpleResponse requestReply(io.netifi.testing.protobuf.SimpleRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REQUEST_REPLY, getCallOptions(), request);
    }

    /**
     * <pre>
     * Fire-and-Forget
     * </pre>
     */
    public com.google.protobuf.Empty fireAndForget(io.netifi.testing.protobuf.SimpleRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_FIRE_AND_FORGET, getCallOptions(), request);
    }

    /**
     * <pre>
     * Single Request / Streaming Response
     * </pre>
     */
    public java.util.Iterator<io.netifi.testing.protobuf.SimpleResponse> requestStream(
        io.netifi.testing.protobuf.SimpleRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_REQUEST_STREAM, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SimpleServiceFutureStub extends io.grpc.stub.AbstractStub<SimpleServiceFutureStub> {
    private SimpleServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SimpleServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SimpleServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SimpleServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Request / Response
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.netifi.testing.protobuf.SimpleResponse> requestReply(
        io.netifi.testing.protobuf.SimpleRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REQUEST_REPLY, getCallOptions()), request);
    }

    /**
     * <pre>
     * Fire-and-Forget
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> fireAndForget(
        io.netifi.testing.protobuf.SimpleRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_FIRE_AND_FORGET, getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST_REPLY = 0;
  private static final int METHODID_FIRE_AND_FORGET = 1;
  private static final int METHODID_REQUEST_STREAM = 2;
  private static final int METHODID_STREAMING_REQUEST_SINGLE_RESPONSE = 3;
  private static final int METHODID_STREAMING_REQUEST_AND_RESPONSE = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SimpleServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SimpleServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST_REPLY:
          serviceImpl.requestReply((io.netifi.testing.protobuf.SimpleRequest) request,
              (io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse>) responseObserver);
          break;
        case METHODID_FIRE_AND_FORGET:
          serviceImpl.fireAndForget((io.netifi.testing.protobuf.SimpleRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_REQUEST_STREAM:
          serviceImpl.requestStream((io.netifi.testing.protobuf.SimpleRequest) request,
              (io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_STREAMING_REQUEST_SINGLE_RESPONSE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamingRequestSingleResponse(
              (io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse>) responseObserver);
        case METHODID_STREAMING_REQUEST_AND_RESPONSE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamingRequestAndResponse(
              (io.grpc.stub.StreamObserver<io.netifi.testing.protobuf.SimpleResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SimpleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SimpleServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.netifi.testing.protobuf.SimpleServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SimpleService");
    }
  }

  private static final class SimpleServiceFileDescriptorSupplier
      extends SimpleServiceBaseDescriptorSupplier {
    SimpleServiceFileDescriptorSupplier() {}
  }

  private static final class SimpleServiceMethodDescriptorSupplier
      extends SimpleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SimpleServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SimpleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SimpleServiceFileDescriptorSupplier())
              .addMethod(METHOD_REQUEST_REPLY)
              .addMethod(METHOD_FIRE_AND_FORGET)
              .addMethod(METHOD_REQUEST_STREAM)
              .addMethod(METHOD_STREAMING_REQUEST_SINGLE_RESPONSE)
              .addMethod(METHOD_STREAMING_REQUEST_AND_RESPONSE)
              .build();
        }
      }
    }
    return result;
  }
}
