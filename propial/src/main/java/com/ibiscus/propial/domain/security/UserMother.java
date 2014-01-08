package com.ibiscus.propial.domain.security;

public class UserMother {

  public static User getJuanPerez() {
    User user = new User(10l, "juanperez", "pass1234", "Juan Perez",
        "juan@gmail.com", "customer");
    user.update("http://picture.com/pic");
    return user;
  }

}
