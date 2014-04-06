package com.ibiscus.propial.domain.security;

public final class ContractMother {

  public static Contract getPropial() {
    Contract contract = new Contract(10l, Contract.TYPE.REALSTATE, "Propial");
    contract.update("Propial", "Guayaquil 424", "4902-6229",
        "propial@gmail.com");
    return contract;
  }

  public static Contract getGalatea() {
    Contract contract = new Contract(15l, Contract.TYPE.REALSTATE, "Galatea");
    contract.update("Galatea", "Acoyte 85", "4901-4514", "galatea@gmail.com");
    return contract;
  }
}
