# RSocket RPC
Requires Protobuf 3.6.1

### 92 megs
`java  -Xmx92m -Xms92m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -verbose:gc -Dhost=172.16.0.6  -Dcount=100000000 -Dconcurrency=192  -Dthreads=16 -cp ./rsocket-rpc-1.0.0.jar io.netifi.thunderdome.rsocketrpc.MultithreadedRSocketRpcRequestReplyClient`

`java -Xmx92m -Xms92m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -verbose:gc -Dhost=0.0.0.0 -cp ./rsocket-rpc-1.0.0.jar io.netifi.thunderdome.rsocketrpc.RSocketRpcServer`

### 128 megs
`java  -Xmx128m -Xms128m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -verbose:gc -Dhost=172.16.0.6  -Dcount=100000000 -Dconcurrency=192  -Dthreads=16 -cp ./rsocket-rpc-1.0.0.jar io.netifi.thunderdome.rsocketrpc.MultithreadedRSocketRpcRequestReplyClient`

`java -Xmx128m -Xms128m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -verbose:gc -Dhost=0.0.0.0 -cp ./rsocket-rpc-1.0.0.jar io.netifi.thunderdome.rsocketrpc.RSocketRpcServer`

### 2 gigs
`java  -Xmx2g -Xms2g -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=172.16.0.6  -Dcount=100000000 -Dconcurrency=128 -Dthreads=16 -cp ./rsocket-rpc-1.0.0.jar io.netifi.thunderdome.rsocketrpc.MultithreadedRSocketRpcRequestReplyClient`

`java -Xmx2g -Xms2g -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=0.0.0.0 -cp ./rsocket-rpc-1.0.0.jar io.netifi.thunderdome.rsocketrpc.RSocketRpcServer`

  