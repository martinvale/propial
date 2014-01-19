package com.ibiscus.propial.web.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.ContractRepository;
import com.ibiscus.propial.web.utils.Packet;
import com.ibiscus.propial.web.utils.ResultSet;

@Controller
@RequestMapping(value="/services/contracts")
public class ContractController {

  /** Repository of users. */
  @Autowired
  private ContractRepository contractRepository;

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public @ResponseBody Contract create(@RequestBody Contract contract) {
    contractRepository.save(contract);
    return contract;
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public @ResponseBody Contract update(@RequestBody Contract contract) {
    contractRepository.save(contract);
    return contract;
  }

  @RequestMapping(value = "/{contractId}", method = RequestMethod.GET)
  public @ResponseBody Packet<Contract> get(@PathVariable long contractId) {
    Packet<Contract> result;
    Contract contract = contractRepository.get(contractId);
    if (contract != null) {
      result = new Packet<Contract>(contract);
    } else {
      result = new Packet<Contract>();
    }
    return result;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public @ResponseBody ResultSet<Contract> get(@RequestParam int start,
      @RequestParam int limit) {
    return contractRepository.find(start, limit);
  }

  @RequestMapping(value = "/{contractId}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long contractId) {
    contractRepository.delete(contractId);
    return true;
  }
}
