package com.ibiscus.propial.domain.security;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

public class UserTest {

  @Test
  public void constructor() {
    User user = new User(10l, "juanperez", "pass", "Juan Perez",
        "juan@gmail.com", "customer");
    assertThat(user.getId(), is(10l));
    assertThat(user.getUsername(), is("juanperez"));
    assertThat(user.getPassword(), is("pass"));
    assertThat(user.getDisplayName(), is("Juan Perez"));
    assertThat(user.getEmail(), is("juan@gmail.com"));
    assertThat(user.getRole(), is("customer"));
  }

  @Test
  public void update() {
    User user = new User(10l, "juanperez", "pass", "Juan Perez",
        "juan@gmail.com", "customer");
    user.update("http://picture.com");
    assertThat(user.getPicture(), is("http://picture.com"));
  }
}
