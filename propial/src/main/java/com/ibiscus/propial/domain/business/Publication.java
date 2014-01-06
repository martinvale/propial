package com.ibiscus.propial.domain.business;

import java.util.Date;

public class Publication {

  /** Fecha de creación del aviso. */
  private Date creation;

  /** El inmueble que se esta publicando. */
  private Inmueble inmueble;

  public Publication() {
    creation = new Date();
  }

  public Date getCreation() {
    return creation;
  }

  public Inmueble getInmueble() {
    return inmueble;
  }

  public String getTitle() {
    return inmueble.getTipo();
  }
}
