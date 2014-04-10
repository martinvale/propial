package com.ibiscus.propial.web.controller.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.ContractRepository;
import com.ibiscus.propial.web.utils.Packet;
import com.ibiscus.propial.web.utils.QueryResults;

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

  @SuppressWarnings("deprecation")
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public @ResponseBody Packet<Contract> update(Long id, String name, String address,
      String telephone, String email, Boolean changeImage,
      HttpServletRequest request) {
    BlobstoreService blobService = BlobstoreServiceFactory
        .getBlobstoreService();
    String uploadUrl = blobService.createUploadUrl("/services/contracts/");
    Map<String, BlobKey> blobs = blobService.getUploadedBlobs(request);
    BlobKey pictureKey = blobs.get("logo");

    Contract contract;
    if (id != null) {
      contract = contractRepository.get(id);
    } else {
      contract = new Contract(Contract.TYPE.REALSTATE, name);
    }
    contract.update(name, address, telephone, email);
    if (changeImage) {
      if (contract.getLogo() != null) {
        blobService.delete(contract.getLogo());
      }
      contract.updateLogo(pictureKey);
    }
    contractRepository.save(contract);
    return new Packet<Contract>(contract, uploadUrl);
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
  public @ResponseBody QueryResults<Contract> get(@RequestParam int start,
      @RequestParam int limit) {
    return contractRepository.find(start, limit);
  }

  @RequestMapping(value = "/{contractId}", method = RequestMethod.DELETE)
  public @ResponseBody boolean delete(@PathVariable long contractId) {
    contractRepository.delete(contractId);
    return true;
  }
}
