package jp.bootware.template.springauthbackend.infrastructure.authentication;

import jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.CipherUtil;
import jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.aes.AESCipherUtilImpl;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenUtil;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenValidation;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.jwt.JSONWebTokenUtilImpl;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.jwt.JSONWebTokenValidationImpl;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.UserService;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.impl.UserDetailsServiceImpl;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AuthenticationConfiguration {

  @Bean
  CipherUtil cipherService() {
    return new AESCipherUtilImpl();
  }

  @Bean
  TokenUtil tokenUtil() {
    return new JSONWebTokenUtilImpl();
  }

  @Bean
  TokenValidation tokenValidation() {
    return new JSONWebTokenValidationImpl();
  }

  @Bean
  UserService userService() {
    return new UserServiceImpl();
  }

  @Bean
  UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }
}
