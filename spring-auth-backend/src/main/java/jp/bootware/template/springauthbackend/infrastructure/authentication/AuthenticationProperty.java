package jp.bootware.template.springauthbackend.infrastructure.authentication;

import jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.CipherProperty;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Data
public class AuthenticationProperty {
//
//  @Autowired CipherProperty cipherProperty;
//  @Autowired TokenProperty tokenProperty;

  @Bean
  @ConfigurationProperties(prefix = "authentication.cipher")
  CipherProperty cipherProperty() {
    return new CipherProperty();
  }

  @Bean
  @ConfigurationProperties(prefix = "authentication.token")
  TokenProperty tokenProperty() {
    return new TokenProperty();
  }
}
