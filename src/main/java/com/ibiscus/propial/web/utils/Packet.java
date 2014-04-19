package com.ibiscus.propial.web.utils;

import org.apache.commons.lang.Validate;

public class Packet<T> {

  private T data;
  private boolean success;
  private String url;

  public Packet() {
    success = false;
  }

  public Packet(final T theData) {
    Validate.notNull(theData, "The data of the packet cannot be null");
    data = theData;
    success = true;
  }

  public Packet(final T theData, final String theUrl) {
    Validate.notNull(theData, "The data of the packet cannot be null");
    Validate.notNull(theUrl, "The url cannot be null");
    data = theData;
    url = theUrl;
    success = true;
  }

  public T getData() {
    return data;
  }

  public boolean getSuccess() {
    return success;
  }

  public String getUrl() {
    return url;
  }
}
