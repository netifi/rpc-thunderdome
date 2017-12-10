# Ratpack

### 128 megs
`java  -Xmx128m -Xms128m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=172.16.0.6 -Dconcurrency=256 -Dcount=100000000 -cp ./ratpack-1.0.0-all.jar io.netifi.thunderdome.ratpack.RatpackRequestReply`

`java  -Xmx128m -Xms128m -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=0.0.0.0 -cp ./ratpack-1.0.0-all.jar io.netifi.thunderdome.ratpack.RatpackTestServer`

### 2 gigs
`java  -Xmx2g -Xms2g -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=172.16.0.6 -Dconcurrency=256 -Dcount=100000000 -cp ./ratpack-1.0.0-all.jar io.netifi.thunderdome.ratpack.RatpackRequestReply`

`java  -Xmx2g -Xms2g -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Dhost=0.0.0.0 -cp ./ratpack-1.0.0-all.jar io.netifi.thunderdome.ratpack.RatpackTestServer`
