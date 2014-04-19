package com.ibiscus.propial.domain.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class UserRepositoryTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  public void save_and_retrieve() {
    UserRepository repository = new UserRepository();

    User user = repository.get(10l);
    assertNull(user);

    user = UserMother.getJuanPerez();
    long id = repository.save(user);

    user = repository.get(id);
    assertNotNull(user);
  }

  public void delete() {
    UserRepository repository = new UserRepository();

    User user = UserMother.getJuanPerez();
    long id = repository.save(user);

    repository.delete(id);

    user = repository.get(id);
    assertNull(user);
  }
}
