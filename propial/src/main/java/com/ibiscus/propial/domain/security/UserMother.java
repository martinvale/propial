package com.ibiscus.propial.domain.security;

public final class UserMother {

  public static User getJuanPerez() {
    User user = new User(ContractMother.getPropial());
    user.update("juanperez", "pass1234", "Juan Perez",
        "juan@gmail.com", User.ROLE.CUSTOMER_ADMIN);
    user.updatePicture("http://picture.com/pic");
    return user;
  }

}
