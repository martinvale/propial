package com.ibiscus.propial.domain.business;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserMother;

public class PublicationRepositoryTest {

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
    PublicationRepository repository = new PublicationRepository(datastore);
    DatastoreService datastoreField = (DatastoreService) ReflectionTestUtils
        .getField(repository, "datastore");
    assertThat(datastoreField, is(datastore));
  }

  @Test
  public void save_and_retrieve() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PublicationRepository repository = new PublicationRepository(datastore);

    Publication publication = repository.get(10l);
    assertNull(publication);

    User author = UserMother.getJuanPerez();
    publication = new Publication(author);
    List<Ambient> ambients = new ArrayList<Ambient>();
    ambients.add(new Ambient());
    publication.update("type", "address", 80, 1300d, "description", 82000d, 42,
        "$", true, ambients);
    Key key = repository.save(publication);

    publication = repository.get(key.getId());
    assertThat(publication.getAuthor().getId(), is(author.getId()));
    assertNotNull(publication);
  }

  @Test
  public void delete() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PublicationRepository repository = new PublicationRepository(datastore);

    User author = UserMother.getJuanPerez();
    Publication publication = new Publication(author);
    repository.save(publication);

    repository.delete(10l);

    publication = repository.get(10l);
    assertNull(publication);
  }
}
