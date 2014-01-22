package com.ibiscus.propial.domain.business;

import java.util.Date;

public class Publication {

  /** The id of the publication. */
  private long id;

  /** Fecha de creación del aviso. */
  private Date creation;

  /** El inmueble que se esta publicando. */
  private Inmueble inmueble;

  public Publication() {
    creation = new Date();
  }

  /** Gets the id of the publication.
   *
   * @return The id of the publication
   */
  public long getId() {
    return id;
  }

  public Date getCreation() {
    return creation;
  }

  public Inmueble getInmueble() {
    return inmueble;
  }

  public String getTitle() {
    return "Titulo";
  }
}
