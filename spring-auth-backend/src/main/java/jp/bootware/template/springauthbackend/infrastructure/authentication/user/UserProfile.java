package jp.bootware.template.springauthbackend.infrastructure.authentication.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserProfile {

  private String userId;
  private String email;
  private String username;
  private Set<String> roles;
  private Set<String> authActions;
}
