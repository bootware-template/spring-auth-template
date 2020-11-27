package jp.bootware.template.springauthbackend.infrastructure.authentication.user.custom;

import jp.bootware.template.springauthbackend.infrastructure.authentication.AuthenticationController;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.LoginRequest;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

  @Autowired
  AuthenticationController authenticationController;

  @Autowired
  UserService userService;

  @Test
  void getProfile() {

    LoginRequest request = new LoginRequest();
    request.setLoginId("User1");
    request.setPassword("password");
    authenticationController.login(null, null, request);

    Assert.assertEquals("Failed get profile", "User1", userService.getUserProfile().getUsername());
  }
}