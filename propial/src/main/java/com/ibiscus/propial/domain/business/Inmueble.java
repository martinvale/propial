package com.ibiscus.propial.domain.business;

import java.util.ArrayList;
import java.util.List;

public class Inmueble {

  private String tipo;
  private String direccion;
  private Integer antiguedad;

  private String codigo;

  private Double expensas;
  private String descripcion;
  private Double precio;
  private Integer superficie;
  private String tipoMoneda;
  private boolean aptoProfesional = false;
  private List<Ambiente> ambientes = new ArrayList<Ambiente>();

  public Inmueble() {
  }

  public String getTipo() {
    return tipo;
  }

  public String getDireccion() {
    return direccion;
  }

  public Integer getAntiguedad() {
    return antiguedad;
  }

  public String getCodigo() {
    return codigo;
  }

  public Double getExpensas() {
    return expensas;
  }

  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @return the precio
   */
  public Double getPrecio() {
    return precio;
  }

  /**
   * @return the superficie
   */
  public Integer getSuperficie() {
    return superficie;
  }

  /**
   * @return the tipoMoneda
   */
  public String getTipoMoneda() {
    return tipoMoneda;
  }

  /**
   * @return the aptoProfesional
   */
  public boolean isAptoProfesional() {
    return aptoProfesional;
  }

  public List<Ambiente> getAmbientes() {
    return ambientes;
  }
}
