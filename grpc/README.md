# gRPC

### 128 megs
`java -Xmx128m -Xms128m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=172.16.0.6 -Dcount=100000000 -Dconcurrency=256  -Dthreads=16  -DuseEpoll=true -cp ./grpc-1.0.0-all.jar io.netifi.thunderdome.grpc.nonblocking.rs.MultithreadedGrpcRequestReplyClient`

`java -Xmx128m -Xms128m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=0.0.0.0 -DuseEpoll=true -cp ./grpc-1.0.0-all.jar io.netifi.thunderdome.grpc.nonblocking.rs.GrpcServer`

### 2 gigs
`java -Xmx2g -Xms2g -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=172.16.0.6 -Dcount=100000000 -Dconcurrency=256  -Dthreads=16  -DuseEpoll=true -cp ./grpc-1.0.0-all.jar io.netifi.thunderdome.grpc.nonblocking.rs.MultithreadedGrpcRequestReplyClient`

`java -Xmx2g -Xms2g -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=0.0.0.0 -DuseEpoll=true -cp ./grpc-1.0.0-all.jar io.netifi.thunderdome.grpc.nonblocking.rs.GrpcServer`
