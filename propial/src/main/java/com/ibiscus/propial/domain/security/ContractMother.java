package com.ibiscus.propial.domain.security;

public class ContractMother {

  public static Contract getPropial() {
    Contract contract = new Contract(10l, "Propial");
    contract.update("Guayaquil 424", "4902-6229", "propial@gmail.com", null);
    return contract;
  }

}
