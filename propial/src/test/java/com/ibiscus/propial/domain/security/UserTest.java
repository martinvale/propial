package com.ibiscus.propial.domain.security;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

public class UserTest {

  @Test
  public void constructor_ok() {
    User user = new User("Juan Perez");
    assertThat(user.getDisplayName(), is("Juan Perez"));
  }
}
