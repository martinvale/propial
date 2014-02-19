package com.ibiscus.propial.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Controller
@RequestMapping(value="/admin/")
public class AdminController {

  @RequestMapping(value = "contracts", method = RequestMethod.GET)
  public ModelAndView contracts() {
    return new ModelAndView("contracts");
  }

  @RequestMapping(value = "users", method = RequestMethod.GET)
  public ModelAndView users() {
    return new ModelAndView("users");
  }

  @RequestMapping(value = "publications", method = RequestMethod.GET)
  public String publications(@ModelAttribute("model") ModelMap model) {
    BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
    model.put("uploadUrl", service.createUploadUrl(
        "/services/publications/upload"));
    return "publications";
  }

  @RequestMapping(value = "locations", method = RequestMethod.GET)
  public ModelAndView locations() {
    return new ModelAndView("locations");
  }
}
