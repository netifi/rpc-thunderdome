package io.netifi.thunderdome.grpc.nonblocking.stock;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.netifi.testing.protobuf.SimpleRequest;
import io.netifi.testing.protobuf.SimpleResponse;
import io.netifi.testing.protobuf.SimpleServiceGrpc;
import io.netifi.thunderdome.grpc.nonblocking.rs.MultithreadedGrpcRequestReplyClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GrpcServer {
  private static final Logger logger = LogManager.getLogger(GrpcServer.class);

  static class DefaultService extends SimpleServiceGrpc.SimpleServiceImplBase {
    @Override
    public void requestReply(SimpleRequest request, StreamObserver<SimpleResponse> responseObserver) {
      SimpleResponse response = SimpleResponse.newBuilder().setResponseMessage(request.getRequestMessage()).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
  }
  
  public static void main(String... args) throws Exception {
    logger.info("starting server");

    String host = System.getProperty("host", "127.0.0.1");
    int port = Integer.getInteger("port", 8001);
  
    io.grpc.Server start = ServerBuilder.forPort(port)
                               .addService(new DefaultService())
                               .build()
                               .start();
  
    logger.info("server started");
    start.awaitTermination();
  }
}
