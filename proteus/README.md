# Proteus RPC
Requires Protobuf 3.5.1

### 92 megs
`java  -Xmx92m -Xms92m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -verbose:gc -Dhost=172.16.0.6  -Dcount=100000000 -Dconcurrency=192  -Dthreads=16 -cp ./proteus-1.0.0-all.jar io.netifi.thunderdome.proteus.MultithreadedProteusRequestReplyClient`

`java -Xmx92m -Xms92m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -verbose:gc -Dhost=0.0.0.0 -cp ./proteus-1.0.0-all.jar io.netifi.thunderdome.proteus.ProteusServer`

### 128 megs
`java  -Xmx128m -Xms128m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -verbose:gc -Dhost=172.16.0.6  -Dcount=100000000 -Dconcurrency=192  -Dthreads=16 -cp ./proteus-1.0.0-all.jar io.netifi.thunderdome.proteus.MultithreadedProteusRequestReplyClient`

`java -Xmx128m -Xms128m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -verbose:gc -Dhost=0.0.0.0 -cp ./proteus-1.0.0-all.jar io.netifi.thunderdome.proteus.ProteusServer`

### 2 gigs
`java  -Xmx2g -Xms2g -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=172.16.0.6  -Dcount=100000000 -Dconcurrency=128 -Dthreads=16 -cp ./proteus-1.0.0-all.jar io.netifi.thunderdome.proteus.MultithreadedProteusRequestReplyClient`

`java -Xmx2g -Xms2g -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=0.0.0.0 -cp ./proteus-1.0.0-all.jar io.netifi.thunderde.proteus.ProteusServer`

  