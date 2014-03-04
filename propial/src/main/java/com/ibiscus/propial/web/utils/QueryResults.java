package com.ibiscus.propial.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.ibiscus.propial.domain.filters.Dimension;

public class QueryResults<T> {

  private final List<T> items;

  private final int size;

  private final List<Dimension> filters;

  public QueryResults(final List<T> theItems, final int theSize) {
    this(theItems, theSize, new ArrayList<Dimension>());
  }

  public QueryResults(final List<T> theItems, final int theSize,
      final List<Dimension> theFilters) {
    Validate.notNull(theItems, "The list of items cannot be null");
    Validate.isTrue(theSize >= 0, "The size of the resultset must greater or "
        + "equal than 0");
    Validate.notNull(theFilters, "The filters cannot be null");
    items = theItems;
    size = theSize;
    filters = theFilters;
  }

  public List<T> getItems() {
    return items;
  }

  public int getSize() {
    return size;
  }

  public List<Dimension> getFilters() {
    return filters;
  }
}
