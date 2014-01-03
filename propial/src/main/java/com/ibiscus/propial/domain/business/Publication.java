package com.ibiscus.propial.domain.business;

import java.util.Date;

public class Publication {

  /** Fecha de creación del aviso. */
  private Date creation;

  public Publication() {
    creation = new Date();
  }

  public Date getCreation() {
    return creation;
  }

  public String getTitle() {
    return "Departamento - Caballito";
  }
}
