package jp.bootware.template.springauthbackend.infrastructure.authentication.token.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenProperty;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class JSONWebTokenValidationImpl implements TokenValidation {

  @Autowired TokenProperty property;

  @Override
  public boolean validate(String token) {

    if (token == null) {
      return false;
    }

    try {
      Jwts.parser().setSigningKey(property.getSecret()).parse(token);
      return true;
    } catch (SignatureException ex) {
      log.warn("Token Validation Error : {}", ex);
    } catch (MalformedJwtException ex) {
      log.warn("Token Validation Error : {}", ex);
    } catch (ExpiredJwtException ex) {
      log.warn("Token Validation Error : {}", ex);
    } catch (UnsupportedJwtException ex) {
      log.warn("Token Validation Error : {}", ex);
    } catch (IllegalArgumentException ex) {
      log.warn("Token Validation Error : {}", ex);
    }
    return false;
  }
}
