package jp.bootware.template.springauthbackend.infrastructure.web;

import jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.CipherUtil;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class HttpCookieUtil {

  @Autowired CipherUtil cipherUtil;

  public HttpCookie createTokenCookie(String tokenCookieName, Token token) {
    return createTokenCookie(tokenCookieName, token.getTokenValue(), token.getDuration());
  }

  public HttpCookie createTokenCookie(String tokenCookieName, String token, Long duration) {
    String encryptedToken = cipherUtil.encrypt(token);
    return ResponseCookie.from(tokenCookieName, encryptedToken)
        .maxAge(duration)
        .httpOnly(true)
        .path("/")
        .build();
  }

  public HttpCookie createDeleteTokenCookie(String tokenCookieName) {
    return ResponseCookie.from(tokenCookieName, "")
        .maxAge(0)
        .httpOnly(true)
        .path("/")
        .build();
  }
}
