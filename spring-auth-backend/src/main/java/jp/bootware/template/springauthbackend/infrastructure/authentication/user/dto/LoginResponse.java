package jp.bootware.template.springauthbackend.infrastructure.authentication.user.dto;

import jp.bootware.template.springauthbackend.infrastructure.web.ResponseSuccessFailure;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

  private ResponseSuccessFailure status;
  private String message;
}
