package jp.bootware.template.springauthbackend.infrastructure.authentication.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

  @NotBlank(message = "Login ID cannot be empty")
  @Email(message = "Please provide valid Login ID")
  private String loginId;

  @NotBlank(message = "Password cannot be empty")
  private String password;
}
