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

public class ContractRepositoryTest {

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
    ContractRepository repository = new ContractRepository(datastore);
    DatastoreService datastoreField = (DatastoreService) ReflectionTestUtils
        .getField(repository, "datastore");
    assertThat(datastoreField, is(datastore));
  }

  public void save_and_retrieve() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ContractRepository repository = new ContractRepository(datastore);

    Contract contract = repository.get(10l);
    assertNull(contract);

    contract = ContractMother.getPropial();
    repository.save(contract);

    contract = repository.get(10l);
    assertNotNull(contract);
  }

  public void delete() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ContractRepository repository = new ContractRepository(datastore);

    Contract contract = ContractMother.getPropial();
    repository.save(contract);

    repository.delete(10l);

    contract = repository.get(10l);
    assertNull(contract);
  }
}
