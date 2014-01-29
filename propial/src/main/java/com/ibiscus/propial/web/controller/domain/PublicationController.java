package com.ibiscus.propial.web.controller.domain;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.business.Ambient;
import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;
import com.ibiscus.propial.web.utils.Packet;
import com.ibiscus.propial.web.utils.ResultSet;

@Controller
@RequestMapping(value="/services/publications")
public class PublicationController {

  /** Repository of publications. */
  @Autowired
  private PublicationRepository publicationRepository;

  /** Repository of users. */
  @Autowired
  private UserRepository usersRepository;

  @RequestMapping(value = "/save", method = RequestMethod.GET)
  public @ResponseBody Packet<Publication> save(Long id, String type, String address,
      Integer age, Double expenses, String description, Double price,
      Integer surface, String currencyType, boolean forProfessional) {
	Publication publication;
	if (id != null) {
		publication = publicationRepository.get(id);		
	} else {
		User user = usersRepository.get(1l);
		publication = new Publication(user);
	}
    publication.update(type, address, age, expenses, description, price,
        surface, currencyType, forProfessional, new ArrayList<Ambient>());
    publicationRepository.save(publication);
    return new Packet<Publication>(publication);
  }

  @RequestMapping(value = "/{publicationId}", method = RequestMethod.GET)
  public @ResponseBody Packet<Publication> get(
      @PathVariable long publicationId) {
    Packet<Publication> result;
    Publication publication = publicationRepository.get(publicationId);
    if (publication != null) {
      result = new Packet<Publication>(publication);
    } else {
      result = new Packet<Publication>();
    }
    return result;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public @ResponseBody ResultSet<Publication> get(@RequestParam int start,
      @RequestParam int limit) {
    return publicationRepository.find(start, limit, null, true, null);
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long userId) {
    publicationRepository.delete(userId);
    return true;
  }
}
