package io.netifi.thunderdome.ratpack;

import com.fasterxml.jackson.databind.ObjectMapper;
import ratpack.http.Request;
import ratpack.http.Response;

public class RatpackTestServer {

  public static void main(String... args) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    ratpack.server.RatpackServer.start(
        server -> {
          server.handlers(
              chain -> {
                chain.post(
                    "",
                    ctx -> {
                      Request request = ctx.getRequest();
                      request
                          .getBody()
                          .then(
                              data -> {
                                SimpleRequest simpleRequest =
                                    mapper.readValue(data.getInputStream(), SimpleRequest.class);
                                SimpleResponse simpleResponse = new SimpleResponse();
                                simpleResponse.setMessage(simpleRequest.getMessage());
                                String s = mapper.writeValueAsString(simpleResponse);
                                Response response = ctx.getResponse();
                                response
                                    .contentTypeIfNotSet("application/javascript")
                                    .status(200)
                                    .send(s);
                              });
                    });
              });
        });
  }
}
