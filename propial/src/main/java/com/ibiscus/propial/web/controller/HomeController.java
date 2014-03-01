package com.ibiscus.propial.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibiscus.propial.domain.business.Publication;
import com.ibiscus.propial.domain.business.PublicationRepository;
import com.ibiscus.propial.web.utils.ResultSet;

@Controller
public class HomeController {

  /** The page size of publications. */
  private static final int PAGE_SIZE = 50;

  /** Repository of publications. */
  @Autowired
  private PublicationRepository publicationRepository;

  @RequestMapping(value = "/")
  public String home(@ModelAttribute("model") ModelMap model) {
    ResultSet<Publication> publications = publicationRepository.find(0,
        PAGE_SIZE, "creation", false, null);
    model.addAttribute("publications", publications.getItems());
    return "home";
  }
}
