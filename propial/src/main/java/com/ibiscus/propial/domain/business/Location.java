package com.ibiscus.propial.domain.business;

import org.apache.commons.lang.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Location {

  @Id
  private Long id;

  private String name;
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

  public Location getParent() {
    return parent.get();
  }
}
