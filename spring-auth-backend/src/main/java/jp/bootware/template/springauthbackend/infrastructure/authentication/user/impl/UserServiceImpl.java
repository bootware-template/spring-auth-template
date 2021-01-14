package jp.bootware.template.springauthbackend.infrastructure.authentication.user.impl;

import jp.bootware.template.springauthbackend.entity.*;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.Token;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenProperty;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenUtil;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenValidation;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.MUserRepository;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.UserProfile;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.UserService;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.dto.LoginRequest;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.dto.LoginResponse;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.dto.LogoutResponse;
import jp.bootware.template.springauthbackend.infrastructure.web.HttpCookieUtil;
import jp.bootware.template.springauthbackend.infrastructure.web.ResponseSuccessFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  HttpCookieUtil httpCookieUtil;
  @Autowired
  TokenProperty tokenProperty;
  @Autowired
  TokenUtil tokenUtil;
  @Autowired
  TokenValidation tokenValidation;
  @Autowired
  MUserRepository repository;
  @Autowired
  UserSpecification specification;

  @Override
  public ResponseEntity<LoginResponse> login(
      LoginRequest loginRequest, String accessToken, String refreshToken) {

    String loginId = loginRequest.getLoginId();
    MUserEntity user = repository.findByEmailOrUsername(loginId);

    boolean accessTokenValid = tokenValidation.validate(accessToken);
    boolean refreshTokenValid = tokenValidation.validate(refreshToken);

    HttpHeaders responseHeaders = new HttpHeaders();

    if (specification.invalidAllTokens(accessTokenValid, refreshTokenValid)
        || specification.validAllTokens(accessTokenValid, refreshTokenValid)) {

      Token newAccessToken = tokenUtil.generateAccessToken(user.getUserName());
      HttpCookie accessTokenCookie = httpCookieUtil.createTokenCookie(
          tokenProperty.getAccessTokenCookieName(), newAccessToken);
      responseHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());

      Token newRefreshToken = tokenUtil.generateRefreshToken(user.getUserName());
      HttpCookie refreshTokenCookie = httpCookieUtil.createTokenCookie(
          tokenProperty.getRefreshTokenCookieName(), newRefreshToken);
      responseHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

    } else if (specification.invalidAccessToken(accessTokenValid, refreshTokenValid)) {

      Token newAccessToken = tokenUtil.generateAccessToken(user.getUserName());
      HttpCookie accessTokenCookie = httpCookieUtil.createTokenCookie(
          tokenProperty.getAccessTokenCookieName(), newAccessToken);
      responseHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());

    }

    LoginResponse loginResponse = new LoginResponse(ResponseSuccessFailure.SUCCESS,
        "Auth successful. Tokens are created in cookie.");

    return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
  }

  @Override
  public ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken) {

    boolean refreshTokenValid = tokenValidation.validate(refreshToken);

    if (!refreshTokenValid) {
      throw new IllegalArgumentException("Refresh Token is invalid!");
    }

    String currentUserEmail = tokenUtil.getUsernameFromToken(accessToken);

    Token newAccessToken = tokenUtil.generateAccessToken(currentUserEmail);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(HttpHeaders.SET_COOKIE, httpCookieUtil
        .createTokenCookie(tokenProperty.getAccessTokenCookieName(),
            newAccessToken.getTokenValue(), newAccessToken.getDuration())
        .toString());

    LoginResponse loginResponse = new LoginResponse(ResponseSuccessFailure.SUCCESS,
        "Auth successful. Tokens are created in cookie.");
    return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
  }

  @Override
  public ResponseEntity<LogoutResponse> logout() {

    HttpHeaders responseHeaders = new HttpHeaders();

    HttpCookie deleteAccessTokenCookie = httpCookieUtil.createDeleteTokenCookie(
        tokenProperty.getAccessTokenCookieName());
    responseHeaders.add(HttpHeaders.SET_COOKIE, deleteAccessTokenCookie.toString());

    HttpCookie deleteRefreshTokenCookie = httpCookieUtil.createDeleteTokenCookie(
        tokenProperty.getRefreshTokenCookieName());
    responseHeaders.add(HttpHeaders.SET_COOKIE, deleteRefreshTokenCookie.toString());

    LogoutResponse logoutResponse =
        new LogoutResponse(ResponseSuccessFailure.SUCCESS, "Logout successful.");

    return ResponseEntity.ok().headers(responseHeaders).body(logoutResponse);
  }

  @Override
  public UserProfile getUserProfile() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl customUserDetails = (UserDetailsImpl) authentication.getPrincipal();

    MUserEntity user = repository.findByEmailOrUsername(customUserDetails.getUsername());

    UserProfile profile = new UserProfile();
    profile.setUserId(user.getId());
    profile.setUsername(user.getUserName());
    profile.setEmail(user.getMailAddress());
    profile.setRoles(user.getTUserRoles().stream()
        .map(TUserRoleEntity::getMRole)
        .map(MRoleEntity::getRoleName)
        .collect(Collectors.toSet()));
    profile.setAuthActions(user.getTUserActionAuthoritys().stream()
        .map(TUserActionAuthorityEntity::getMActionAuthority)
        .map(MActionAuthorityEntity::getActionName)
        .collect(Collectors.toSet()));

    return profile;
  }
}
