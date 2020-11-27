package jp.bootware.template.springauthbackend.infrastructure.authentication.token.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.CipherUtil;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.Token;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.Token.TokenType;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenProperty;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JSONWebTokenUtilImpl implements TokenUtil {

  @Autowired
  TokenProperty tokenProperty;
  @Autowired
  CipherUtil cipherUtil;

  @Override
  public Token generateAccessToken(String subject) {

    Date now = new Date();
    Long duration = now.getTime() + tokenProperty.getAccessTokenExpirationMsec();
    Date expiryDate = new Date(duration);

    String token = Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, tokenProperty.getSecret())
        .compact();

    return new Token(Token.TokenType.ACCESS, token, duration,
        LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
  }
  
  @Override
  public Token generateRefreshToken(String subject) {

    Date now = new Date();
    Long duration = now.getTime() + tokenProperty.getRefreshTokenExpirationMsec();
    Date expiryDate = new Date(duration);

    String token = Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, tokenProperty.getSecret())
        .compact();

    return new Token(TokenType.REFRESH, token, duration,
        LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
  }

  @Override
  public String getUsernameFromToken(String token) {

    Claims claims = Jwts.parser()
        .setSigningKey(tokenProperty.getSecret())
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  @Override
  public LocalDateTime getExpiryDateFromToken(String token) {

    Claims claims = Jwts.parser()
        .setSigningKey(tokenProperty.getSecret())
        .parseClaimsJws(token)
        .getBody();

    return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
  }

  public String getJwtFromRequest(HttpServletRequest request) {

    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      String accessToken = bearerToken.substring(7);
      if (accessToken == null) {
        return null;
      }

      return cipherUtil.decrypt(accessToken);
    }
    return null;
  }

  public String getJwtFromCookie(HttpServletRequest request) {

    Cookie[] cookies = request.getCookies();

    if (cookies == null) {
      return null;
    }

    for (Cookie cookie : cookies) {
      if (tokenProperty.getAccessTokenCookieName().equals(cookie.getName())) {
        String accessToken = cookie.getValue();
        if (accessToken == null) {
          return null;
        }

        return cipherUtil.decrypt(accessToken);
      }
    }
    return null;
  }
}
