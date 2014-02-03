package com.ibiscus.propial.domain.security;

public final class ContractMother {

  public static Contract getPropial() {
    Contract contract = new Contract(10l, "Propial");
    contract.update("Guayaquil 424", "4902-6229", "propial@gmail.com", null);
    return contract;
  }

  public static Contract getGalatea() {
    Contract contract = new Contract(15l, "Galatea");
    contract.update("Acoyte 85", "4901-4514", "galatea@gmail.com", null);
    return contract;
  }
}
