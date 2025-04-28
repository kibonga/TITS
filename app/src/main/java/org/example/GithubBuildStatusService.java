package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class GithubBuildStatusService {

  private final String method;
  private final String url;
  private final Map<String, String> headers;
  private boolean output = true;

  private GithubBuildStatusService(Builder builder) {
    this.method = builder.method;
    this.url = builder.url;
    this.headers = builder.headers;
    this.output = builder.output;
  }

  public HttpsURLConnection createConnection() throws URISyntaxException, IOException {
    final HttpsURLConnection connection =
        (HttpsURLConnection) new URI(this.url).toURL().openConnection();

    connection.setRequestMethod(this.method);
    for (Map.Entry<String, String> header : this.headers.entrySet()) {
      connection.setRequestProperty(header.getKey(), header.getValue());
    }
    connection.setDoOutput(this.output);

    return connection;
  }

  public static class Builder {

    private String method;
    private String url;
    private final Map<String, String> headers = new HashMap<>();
    private boolean output = true;

    public Builder method(String method) {
      this.method = method;
      return this;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder headers(String key, String value) {
      this.headers.put(key, value);
      return this;
    }

    public Builder output(boolean output) {
      this.output = output;
      return this;
    }

    public GithubBuildStatusService build() {
      return new GithubBuildStatusService(this);
    }
  }
}
