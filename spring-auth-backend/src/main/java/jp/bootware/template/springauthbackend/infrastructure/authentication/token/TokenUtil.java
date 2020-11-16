package jp.bootware.template.springauthbackend.infrastructure.authentication.token;

import java.time.LocalDateTime;

public interface TokenUtil {

  Token generateAccessToken(String subject);

  Token generateRefreshToken(String subject);

  String getUsernameFromToken(String token);

  LocalDateTime getExpiryDateFromToken(String token);
}
