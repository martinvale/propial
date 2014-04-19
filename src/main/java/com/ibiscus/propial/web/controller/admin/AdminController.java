package com.ibiscus.propial.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;

@Controller
@RequestMapping(value="/admin/")
public class AdminController {

  /** Repository of users. */
  @Autowired
  private UserRepository userRepository;

  @RequestMapping(value = "contracts")
  public String contracts(@ModelAttribute("model") ModelMap model) {
    addContext(model);
    BlobstoreService blobService = BlobstoreServiceFactory
        .getBlobstoreService();
    model.put("uploadUrl", blobService.createUploadUrl("/services/contracts/"));
    return "contracts";
  }

  @RequestMapping(value = "users")
  public String users(@ModelAttribute("model") ModelMap model) {
    addContext(model);
    return "users";
  }

  @RequestMapping(value = "")
  public String publications(@ModelAttribute("model") ModelMap model) {
    addContext(model);
    BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
    model.put("uploadUrl", service.createUploadUrl(
        "/services/publications/upload"));
    return "publications";
  }

  @RequestMapping(value = "publish")
  public String publish(@ModelAttribute("model") ModelMap model) {
    addContext(model);
    model.put("publish", "true");
    BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
    model.put("uploadUrl", service.createUploadUrl(
        "/services/publications/upload"));
    return "publications";
  }

  @RequestMapping(value = "locations")
  public ModelAndView locations(@ModelAttribute("model") ModelMap model) {
    addContext(model);
    return new ModelAndView("locations");
  }

  private void addContext(ModelMap model) {
    UserService userService = UserServiceFactory.getUserService();
    model.addAttribute("loginurl", userService.createLoginURL("/"));
    model.addAttribute("logouturl", userService.createLogoutURL("/"));
    com.google.appengine.api.users.User googleUser = userService
        .getCurrentUser();
    if (googleUser != null) {
      User user = userRepository.findByEmail(googleUser.getEmail());
      model.addAttribute("user", user);
    }
  }
}
