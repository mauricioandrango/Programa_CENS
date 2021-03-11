package com.cens.backend.censbackend.utils;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
 
/**
 * The Class HeaderRequestInterceptor.
 * @author rnaranjo & fmaldonado
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
 
  /** The header name. */
  private final String headerName;
  
  /** The header value. */
  private final String headerValue;
 
  /**
   * Instantiates a new header request interceptor.
   *
   * @param headerName the header name
   * @param headerValue the header value
   */
  public HeaderRequestInterceptor(String headerName, String headerValue) {
    this.headerName = headerName;
    this.headerValue = headerValue;
  }
 
  /**
   * Intercept.
   *
   * @param request the request
   * @param body the body
   * @param execution the execution
   * @return the client http response
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
      throws IOException {
    HttpRequest wrapper = new HttpRequestWrapper(request);
    wrapper.getHeaders().set(headerName, headerValue);
    return execution.execute(wrapper, body);
  }
}