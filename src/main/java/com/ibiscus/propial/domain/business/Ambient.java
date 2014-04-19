package com.ibiscus.propial.domain.business;

import com.googlecode.objectify.annotation.Embed;

@Embed
public class Ambient {

  private String type;
  private String observation;
  private String dimension;

  public Ambient() {
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
  /**
   * @return the observation
   */
  public String getObservation() {
    return observation;
  }
  /**
   * @return the dimension
   */
  public String getDimension() {
    return dimension;
  }
}
