package com.ibiscus.propial.domain.security;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

public class ContractTest {

  @Test
  public void constructor() {
    Contract contract = new Contract(10l, "nombre");
    assertThat(contract.getId(), is(10l));
    assertThat(contract.getName(), is("nombre"));
    assertNull(contract.getAddress());
    assertNull(contract.getTelephone());
    assertNull(contract.getEmail());
    assertNull(contract.getLogo());
  }

  @Test
  public void update() {
    Contract contract = new Contract(10l, "nombre");
    contract.update("address", "4545", "email@email.com", "logo");
    assertThat(contract.getId(), is(10l));
    assertThat(contract.getName(), is("nombre"));
    assertThat(contract.getAddress(), is("address"));
    assertThat(contract.getTelephone(), is("4545"));
    assertThat(contract.getEmail(), is("email@email.com"));
    assertThat(contract.getLogo(), is("logo"));
  }
}
