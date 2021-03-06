package jp.bootware.template.springauthbackend.infrastructure.authentication.user.impl;

import jp.bootware.template.springauthbackend.infrastructure.authentication.AuthenticationController;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.UserService;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.dto.LoginRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {

  @Autowired
  AuthenticationController authenticationController;

  @Autowired
  UserService userService;

  @Test
  public void getProfile() {

    LoginRequest request = new LoginRequest();
    request.setLoginId("User1");
    request.setPassword("password");
    authenticationController.login(null, null, request);

    Assert.assertEquals("Failed get profile", "User1", userService.getUserProfile().getUsername());
  }
}