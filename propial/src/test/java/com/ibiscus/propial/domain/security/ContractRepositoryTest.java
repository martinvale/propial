package com.ibiscus.propial.domain.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.ibiscus.propial.web.security.GaeUserAuthentication;
import com.ibiscus.propial.web.utils.ResultSet;

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
  public void save_and_retrieve() {
    ContractRepository repository = new ContractRepository();

    Contract contract = repository.get(10l);
    assertNull(contract);

    contract = ContractMother.getPropial();
    long id = repository.save(contract);

    contract = repository.get(id);
    assertNotNull(contract);
  }

  @Test
  public void delete() {
    ContractRepository repository = new ContractRepository();

    Contract contract = ContractMother.getPropial();
    long id = repository.save(contract);

    repository.delete(id);

    contract = repository.get(id);
    assertNull(contract);
  }

  @Test
  public void find() {
    ContractRepository repository = new ContractRepository();

    User user = UserMother.getJuanPerez();
    Authentication authentication = new GaeUserAuthentication(user, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    Contract contract1 = ContractMother.getPropial();
    repository.save(contract1);
    Contract contract2 = ContractMother.getGalatea();
    repository.save(contract2);

    ResultSet<Contract> result = repository.find(0, 1);
    assertThat(result.getItems().size(), is(1));
    assertThat(result.getItems().get(0).getId(), is(contract1.getId()));
    assertThat(result.getSize(), is(1));
  }
}
