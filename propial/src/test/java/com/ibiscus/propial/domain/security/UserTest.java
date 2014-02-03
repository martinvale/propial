package com.ibiscus.propial.domain.security;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class UserTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig());
  private Contract contract;

  @Before
  public void setUp() {
    helper.setUp();
    contract = ContractMother.getPropial();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void constructor() {
    User user = new User(contract);
    assertThat(user.getContract().getId(), is(contract.getId()));
  }

  @Test
  public void update() {
    User user = new User(contract);
    user.update("juanperez", "pass", "Juan Perez", "juan@gmail.com",
        User.ROLE.CUSTOMER_ADMIN);
    assertThat(user.getContract().getId(), is(contract.getId()));
    assertThat(user.getUsername(), is("juanperez"));
    assertThat(user.getPassword(), is("pass"));
    assertThat(user.getDisplayName(), is("Juan Perez"));
    assertThat(user.getEmail(), is("juan@gmail.com"));
    assertThat(user.getRole(), is(User.ROLE.CUSTOMER_ADMIN));

    user.updatePicture("http://picture.com");
    assertThat(user.getPicture(), is("http://picture.com"));
  }
}
