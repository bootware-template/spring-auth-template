package jp.bootware.template.springauthbackend.infrastructure.authentication.user.impl;

import org.springframework.stereotype.Component;

@Component
public class UserSpecification {

  public boolean invalidAllTokens(boolean accessTokenValid, boolean refreshTokenValid) {
    return !accessTokenValid && !refreshTokenValid;
  }

  public boolean invalidAccessToken(boolean accessTokenValid, boolean refreshTokenValid) {
    return !accessTokenValid && refreshTokenValid;
  }

  public boolean validAllTokens(boolean accessTokenValid, boolean refreshTokenValid) {
    return accessTokenValid && refreshTokenValid;
  }
}
