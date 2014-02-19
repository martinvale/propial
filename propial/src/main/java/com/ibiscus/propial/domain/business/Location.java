package com.ibiscus.propial.domain.business;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Location implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  @Index
  private String name;

  @Index
  private Integer priority = new Integer(0);

  private Ref<Location> parent;

  Location() {
  }

  Location(final Location theParent, final String theName) {
    Validate.notNull(theName, "The name cannot be null");
    name = theName;
    if (theParent != null) {
      parent = Ref.create(Key.create(Location.class, theParent.getId()));
    }
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getPriority() {
    return priority;
  }

  public Location getParent() {
    if (parent != null) {
      return parent.get();
    } else {
      return null;
    }
  }
}
