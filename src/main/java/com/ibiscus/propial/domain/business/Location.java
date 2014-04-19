package com.ibiscus.propial.domain.business;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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
  private List<String> tokenizedName = new LinkedList<String>();

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
    tokenize(theName);
  }

  public void update(final String theName, final Integer thePriority) {
    Validate.notNull(theName, "The name cannot be null");
    Validate.notNull(thePriority, "The priority cannot be null");
    Validate.isTrue(thePriority >= 0, "The priority must be greater or equal "
        + "than zero");
    name = theName;
    priority = thePriority;
    tokenize(theName);
  }

  private void tokenize (final String theName) {
    tokenizedName.clear();
    String lowerName = theName.toLowerCase();
    for (int i = 1; i <= lowerName.length(); i++) {
      tokenizedName.add(lowerName.substring(0, i));
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
