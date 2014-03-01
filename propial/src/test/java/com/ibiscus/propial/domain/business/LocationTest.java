package com.ibiscus.propial.domain.business;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class LocationTest {

  @Test
  @SuppressWarnings("unchecked")
  public void constructor() {
    Location location = new Location(null, "Rio", 0);
    assertNull(location.getParent());
    assertThat(location.getName(), is("Rio"));
    assertThat(location.getPriority(), is(0));
    List<String> tokens = (List<String>) ReflectionTestUtils.getField(location,
        "tokenizedName");
    assertThat(tokens.size(), is(3));
    for (int i = 1; i <= location.getName().length(); i++) {
      assertTrue(tokens.contains(location.getName().toLowerCase()
          .substring(0, i)));
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void update() {
    Location location = new Location(null, "Rio", 0);
    location.update("Minas", 1);
    assertNull(location.getParent());
    assertThat(location.getName(), is("Minas"));
    assertThat(location.getPriority(), is(1));
    List<String> tokens = (List<String>) ReflectionTestUtils.getField(location,
        "tokenizedName");
    assertThat(tokens.size(), is(5));
    for (int i = 1; i <= location.getName().length(); i++) {
      assertTrue(tokens.contains(location.getName().toLowerCase()
          .substring(0, i)));
    }
  }
}
