package com.ibiscus.propial.web.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;
import com.ibiscus.propial.web.utils.Packet;
import com.ibiscus.propial.web.utils.ResultSet;

@Controller
@RequestMapping(value="/services/users")
public class UserController {

  /** Repository of users. */
  @Autowired
  private UserRepository userRepository;

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public @ResponseBody User create(@RequestBody User user) {
    userRepository.save(user);
    return user;
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public @ResponseBody User update(@RequestBody User user) {
    userRepository.save(user);
    return user;
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public @ResponseBody Packet<User> get(@PathVariable long userId) {
    Packet<User> result;
    User user = userRepository.get(userId);
    if (user != null) {
      result = new Packet<User>(user);
    } else {
      result = new Packet<User>();
    }
    return result;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public @ResponseBody ResultSet<User> get(@RequestParam int start,
      @RequestParam int limit) {
    return userRepository.find(start, limit);
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long userId) {
    userRepository.delete(userId);
    return true;
  }
}
