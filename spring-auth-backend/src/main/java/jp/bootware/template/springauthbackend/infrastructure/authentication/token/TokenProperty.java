package jp.bootware.template.springauthbackend.infrastructure.authentication.token;

import lombok.Data;

@Data
public class TokenProperty {

  private String accessTokenCookieName;

  private String refreshTokenCookieName;

  private String secret;

  private Long accessTokenExpirationMsec;

  private Long refreshTokenExpirationMsec;
}
