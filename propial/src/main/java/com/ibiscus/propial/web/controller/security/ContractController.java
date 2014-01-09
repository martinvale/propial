package com.ibiscus.propial.web.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.ContractRepository;

@Controller
@RequestMapping(value="/contracts")
public class ContractController {

  /** Repository of users. */
  @Autowired
  private ContractRepository contractRepository;

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public Contract create(@RequestBody @ModelAttribute Contract contract) {
    contractRepository.save(contract);
    return contract;
  }

  @RequestMapping(value = "/{contractId}", method = RequestMethod.GET)
  public @ResponseBody Contract get(@PathVariable long contractId) {
    return contractRepository.get(contractId);
  }

  @RequestMapping(value = "/{contractId}", method = RequestMethod.DELETE)
  public void delete(@PathVariable long contractId) {
    contractRepository.delete(contractId);
  }
}
