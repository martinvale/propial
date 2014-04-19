package com.ibiscus.propial.domain.business;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.ibiscus.propial.domain.business.Publication.OPERATION;
import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.ContractMother;
import com.ibiscus.propial.domain.security.ContractRepository;
import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserMother;
import com.ibiscus.propial.domain.security.UserRepository;

public class PublicationRepositoryTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig());
  private UserRepository userRepository;
  private LocationRepository locationRepository;
  private ContractRepository contractRepository;
  private User author;
  private Contract contract;
  private Location ciudad;

  @Before
  public void setUp() {
    helper.setUp();
    userRepository = new UserRepository();
    long id = userRepository.save(UserMother.getJuanPerez());
    author = userRepository.get(id);
    contractRepository = new ContractRepository();
    id = contractRepository.save(ContractMother.getGalatea());
    contract = contractRepository.get(id);
    locationRepository = new LocationRepository();
    ciudad = new Location(null, "Buenos Aires", 0);
    locationRepository.save(ciudad);
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void save_and_retrieve() {
    PublicationRepository repository = new PublicationRepository();

    Publication publication = new Publication(contract, author);
    List<Ambient> ambients = new ArrayList<Ambient>();
    ambients.add(new Ambient());
    List<Location> locations = new ArrayList<Location>();
    locations.add(ciudad);
    publication.update("type", OPERATION.ALQUILER, "address", 80, 1300d,
        "description", 82000d, 42, "$", true, ambients, locations);
    long id = repository.save(publication);

    publication = repository.get(id);
    assertThat(publication.getAuthor().getId(), is(author.getId()));
    assertNotNull(publication);
  }

  @Test
  public void delete() {
    PublicationRepository repository = new PublicationRepository();

    Publication publication = new Publication(contract, author);
    long id = repository.save(publication);

    repository.delete(id);

    publication = repository.get(id);
    assertNull(publication);
  }
}
