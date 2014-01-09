package com.ibiscus.propial.web.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;

@Controller
@RequestMapping(value="/users")
public class UserController {

  /** Repository of users. */
  @Autowired
  private UserRepository userRepository;

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public User create(@RequestBody @ModelAttribute User user) {
    userRepository.save(user);
    return user;
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public @ResponseBody User get(@PathVariable long userId) {
    return userRepository.get(userId);
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public void delete(@PathVariable long userId) {
    userRepository.delete(userId);
  }
}
