package com.ibiscus.propial.web.utils;

import org.apache.commons.lang.Validate;

public class Packet<T> {

  private T data;
  private boolean success;

  public Packet() {
    success = false;
  }

  public Packet(final T theData) {
    Validate.notNull(theData, "The data of the packet cannot be null");
    data = theData;
    success = true;
  }

  public T getData() {
    return data;
  }

  public boolean getSuccess() {
    return success;
  }
}
