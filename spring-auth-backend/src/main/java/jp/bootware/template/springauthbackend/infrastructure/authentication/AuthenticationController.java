package jp.bootware.template.springauthbackend.infrastructure.authentication;

import jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.CipherUtil;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.UserProfile;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.UserService;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.dto.LoginRequest;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.dto.LoginResponse;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.dto.LogoutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

  @Autowired AuthenticationManager authenticationManager;
  @Autowired CipherUtil cipherUtil;
  @Autowired UserService userService;

  @PostMapping(value = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoginResponse> login(
      @CookieValue(name = "accessToken", required = false) String accessToken,
      @CookieValue(name = "refreshToken", required = false) String refreshToken,
      @Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(),
            loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String decryptedAccessToken = cipherUtil.decrypt(accessToken);
    String decryptedRefreshToken = cipherUtil.decrypt(refreshToken);

    return userService.login(loginRequest, decryptedAccessToken, decryptedRefreshToken);
  }

  @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoginResponse> refreshToken(
      @CookieValue(name = "accessToken", required = false) String accessToken,
      @CookieValue(name = "refreshToken", required = false) String refreshToken) {

    String decryptedAccessToken = cipherUtil.decrypt(accessToken);
    String decryptedRefreshToken = cipherUtil.decrypt(refreshToken);

    return userService.refresh(decryptedAccessToken, decryptedRefreshToken);
  }

  @PostMapping(value = "/me")
  public ResponseEntity<UserProfile> me() {
    return ResponseEntity.ok(userService.getUserProfile());
  }

  @RequestMapping(value = "/logout")
  public ResponseEntity<LogoutResponse> logout() {
    return userService.logout();
  }
}
