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

  @Index
  private Ref<Location> parent;

  Location() {
  }

  public Location(final Location theParent, final String theName,
      final Integer thePriority) {
    Validate.notNull(theName, "The name cannot be null");
    Validate.notNull(thePriority, "The priority cannot be null");
    Validate.isTrue(thePriority >= 0, "The priority must be greater or equal "
        + "than zero");
    name = theName;
    priority = thePriority;
    if (theParent != null) {
      parent = Ref.create(Key.create(Location.class, theParent.getId()));
    }
  }

  public void update(final String theName, final Integer thePriority) {
    Validate.notNull(theName, "The name cannot be null");
    Validate.notNull(thePriority, "The priority cannot be null");
    Validate.isTrue(thePriority >= 0, "The priority must be greater or equal "
        + "than zero");
    name = theName;
    priority = thePriority;
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
