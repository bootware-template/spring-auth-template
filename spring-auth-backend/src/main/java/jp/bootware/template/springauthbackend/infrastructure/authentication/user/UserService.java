package jp.bootware.template.springauthbackend.infrastructure.authentication.user;

import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<LoginResponse> login(
      LoginRequest loginRequest, String accessToken, String refreshToken);

  ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

  ResponseEntity<LogoutResponse> logout();

  UserProfile getUserProfile();
}
