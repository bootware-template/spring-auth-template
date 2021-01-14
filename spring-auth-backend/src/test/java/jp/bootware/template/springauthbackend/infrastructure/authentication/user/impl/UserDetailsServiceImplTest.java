package jp.bootware.template.springauthbackend.infrastructure.authentication.user.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


@SpringBootTest
public class UserDetailsServiceImplTest {

  @Autowired
  UserDetailsService userDetailsService;

  @Test
  public void loadUserByUsername() {

    UserDetails userDetails = userDetailsService.loadUserByUsername("User1");

    Assert.assertEquals("User1", userDetails.getUsername());
  }
}
