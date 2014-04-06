package com.ibiscus.propial.domain.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ContractTest {

  @Test
  public void constructor() {
    Contract contract = new Contract(10l, Contract.TYPE.REALSTATE, "nombre");
    assertThat(contract.getId(), is(10l));
    assertThat(contract.getName(), is("nombre"));
    assertThat(contract.getType(), is(Contract.TYPE.REALSTATE));
    assertNull(contract.getAddress());
    assertNull(contract.getTelephone());
    assertNull(contract.getEmail());
    assertNull(contract.getLogo());
  }

  @Test
  public void update() {
    Contract contract = new Contract(10l, Contract.TYPE.REALSTATE, "nombre");
    contract.update("nombre", "address", "4545", "email@email.com");
    assertThat(contract.getId(), is(10l));
    assertThat(contract.getName(), is("nombre"));
    assertThat(contract.getType(), is(Contract.TYPE.REALSTATE));
    assertThat(contract.getAddress(), is("address"));
    assertThat(contract.getTelephone(), is("4545"));
    assertThat(contract.getEmail(), is("email@email.com"));
    assertNull(contract.getLogo());
  }
}
