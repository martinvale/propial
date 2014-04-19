package com.ibiscus.propial.domain.security;

public final class UserMother {

  public static User getJuanPerez() {
    User user = new User(ContractMother.getPropial());
    user.update("Juan Perez", User.ROLE.CUSTOMER_ADMIN);
    user.updatePicture("http://picture.com/pic");
    return user;
  }

}
