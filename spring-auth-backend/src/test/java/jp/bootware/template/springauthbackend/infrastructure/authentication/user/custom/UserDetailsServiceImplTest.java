package jp.bootware.template.springauthbackend.infrastructure.authentication.user.custom;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


@SpringBootTest
class UserDetailsServiceImplTest {

  @Autowired UserDetailsService userDetailsService;

  @Test
  void loadUserByUsername() {

    UserDetails userDetails = userDetailsService.loadUserByUsername("User1");

    Assert.assertEquals("User1", userDetails.getUsername());
  }
}
