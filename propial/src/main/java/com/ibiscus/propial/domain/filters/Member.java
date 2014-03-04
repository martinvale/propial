package com.ibiscus.propial.domain.filters;

import org.apache.commons.lang.Validate;

public class Member {

  private final String name;
  private final String value;
  private final int count;

  public Member(final String theName, final String theValue, final int theCount) {
    Validate.notNull(theName, "The name cannot be null");
    Validate.notNull(theValue, "The value cannot be null");
    Validate.isTrue(theCount >= 0, "The count must be greater or equal than 0");
    name = theName;
    value = theValue;
    count = theCount;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public int getCount() {
    return count;
  }
}
