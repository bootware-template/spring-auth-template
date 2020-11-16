package jp.bootware.template.springauthbackend.infrastructure.authentication.user;

import java.util.Set;
import lombok.Data;

@Data
public class UserProfile {

  private String userId;
  private String email;
  private String username;
  private Set<String> roles;
}
