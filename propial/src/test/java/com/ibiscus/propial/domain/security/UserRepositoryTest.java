package com.ibiscus.propial.domain.security;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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

  @Test
  public void constructor() {
    DatastoreService datastore = createMock(DatastoreService.class);
    UserRepository repository = new UserRepository(datastore);
    DatastoreService datastoreField = (DatastoreService) ReflectionTestUtils
        .getField(repository, "datastore");
    assertThat(datastoreField, is(datastore));
  }

  public void save_and_retrieve() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    UserRepository repository = new UserRepository(datastore);

    User user = repository.get(10l);
    assertNull(user);

    user = UserMother.getJuanPerez();
    repository.save(user);

    user = repository.get(10l);
    assertNotNull(user);
  }

  public void delete() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    UserRepository repository = new UserRepository(datastore);

    User user = UserMother.getJuanPerez();
    repository.save(user);

    repository.delete(10l);

    user = repository.get(10l);
    assertNull(user);
  }
}
