package com.ibiscus.propial.domain.filters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

public class Dimension {

  private final String id;
  private final String name;
  private final List<Member> members = new ArrayList<Member>();

  public Dimension (final String theId, final String theName) {
    Validate.notNull(theId, "The id cannot be null");
    Validate.notNull(theName, "The name cannot be null");
    id = theId;
    name = theName;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Member> getMembers() {
    return members;
  }

}
