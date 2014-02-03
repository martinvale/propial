package com.ibiscus.propial.web.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.ContractRepository;
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

  /** Repository of contracts. */
  @Autowired
  private ContractRepository contractsRepository;

  @RequestMapping(value = "/save", method = RequestMethod.GET)
  public @ResponseBody User save(Long id, String username, String password,
      String displayName, String email, String role, Long contractId,
      boolean enabled) {
    User user;
    if (id != null) {
      user = userRepository.get(id);
    } else {
      Contract contract;
      if (contractId != null) {
        contract = contractsRepository.get(contractId);
      } else {
        User currentUser = (User) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        contract = currentUser.getContract();
      }
      user = new User(contract);
    }
    User.ROLE userRole = User.ROLE.valueOf(role);
    user.update(username, password, displayName, email, userRole);
    if (enabled) {
      user.enable();
    } else {
      user.disable();
    }
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
