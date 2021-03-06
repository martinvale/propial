package com.ibiscus.propial.domain.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.security.core.context.SecurityContextHolder;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.ibiscus.propial.infraestructure.objectify.OfyService;
import com.ibiscus.propial.web.utils.QueryResults;

public class ContractRepository {

  public Contract get(final long id) {
    return OfyService.ofy().load().type(Contract.class).id(id).now();
  }

  public QueryResults<Contract> find(final int start, final int limit) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    if (!user.getRole().equals(User.ROLE.CUSTOMER_ADMIN)
        && !user.getRole().equals(User.ROLE.ADMIN)) {
      return new QueryResults<Contract>(new ArrayList<Contract>(), 0);
    }
    Query<Contract> query = OfyService.ofy().load().type(Contract.class)
        .limit(limit).order("name");
    if (start > 0) {
      query = query.offset(start);
    }
    if (user.getRole().equals(User.ROLE.CUSTOMER_ADMIN)) {
      query = query.filter("id", user.getContract().getId());
    }
    List<Contract> contracts = query.list();
    Query<Contract> queryCount = OfyService.ofy().load().type(Contract.class);
    if (user.getRole().equals(User.ROLE.CUSTOMER_ADMIN)) {
      queryCount = queryCount.filter("id", user.getContract().getId());
    }
    int size = queryCount.count();
    return new QueryResults<Contract>(contracts, size);
  }

  public long save(final Contract contract) {
    Validate.notNull(contract, "The contract cannot be null");
    Key<Contract> key = OfyService.ofy().save().entity(contract).now();
    return key.getId();
  }

  /** Deletes a user from the repository.
   *
   * @param id The id of the contract to remove. Must be a value greater than 0.
   */
  public void delete(final long id) {
    Validate.isTrue(id > 0, "The id of the contract must be greater than 0");
    Contract contract = OfyService.ofy().load().type(Contract.class).id(id)
        .now();
    OfyService.ofy().delete().entity(contract).now();
  }
}
