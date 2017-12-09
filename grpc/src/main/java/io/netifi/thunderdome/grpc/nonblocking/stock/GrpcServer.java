package io.netifi.thunderdome.grpc.nonblocking.stock;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleResponse;
import io.netifi.testing.protobuf.SimpleServiceGrpc;

public class GrpcServer {
  static class DefaultService extends SimpleServiceGrpc.SimpleServiceImplBase {
    @Override
    public void requestReply(SimpleRequest request, StreamObserver<SimpleResponse> responseObserver) {
      SimpleResponse response = SimpleResponse.newBuilder().setResponseMessage(request.getRequestMessage()).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
  }
  
  public static void main(String... args) throws Exception {
    System.out.println("starting server");
  
  
    String host = System.getProperty("host", "127.0.0.1");
    int port = Integer.getInteger("port", 8001);
  
    io.grpc.Server start = ServerBuilder.forPort(port)
                               .addService(new DefaultService())
                               .build()
                               .start();
  
    System.out.println("server started");
    start.awaitTermination();
  }
}
