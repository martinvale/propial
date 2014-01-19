package com.ibiscus.propial.web.utils;

import java.util.List;

import org.apache.commons.lang.Validate;

public class ResultSet<T> {

  private final List<T> items;

  private final int size;

  public ResultSet(final List<T> theItems, final int theSize) {
    Validate.notNull(theItems, "The list of items cannot be null");
    Validate.isTrue(theSize >= 0, "The size of the resultset must greater or "
        + "equal than 0");
    items = theItems;
    size = theSize;
  }

  public List<T> getItems() {
    return items;
  }

  public int getSize() {
    return size;
  }
}
