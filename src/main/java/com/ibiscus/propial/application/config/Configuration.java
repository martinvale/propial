package com.ibiscus.propial.application.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.objectify.annotation.EmbedMap;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Configuration implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  @EmbedMap
  private Map<String, String> properties = new HashMap<String, String>();

  /** Default constructor used by Objectify. */
  public Configuration() {
  }

  public String getValue(final String property) {
    return properties.get(property);
  }

  public void setValue(final String property, final String value) {
    properties.put(property, value);
  }
}
