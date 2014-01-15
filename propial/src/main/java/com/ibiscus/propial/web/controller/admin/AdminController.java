package com.ibiscus.propial.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/admin/")
public class AdminController {

  @RequestMapping(value = "contracts", method = RequestMethod.GET)
  public ModelAndView contracts() {
    return new ModelAndView("contracts");
  }

}
