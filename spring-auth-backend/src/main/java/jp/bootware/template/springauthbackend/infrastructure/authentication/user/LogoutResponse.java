package jp.bootware.template.springauthbackend.infrastructure.authentication.user;

import jp.bootware.template.springauthbackend.infrastructure.web.ResponseSuccessFailure;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutResponse {

  private ResponseSuccessFailure status;
  private String message;
}

