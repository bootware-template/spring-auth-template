package jp.bootware.template.springauthbackend.infrastructure.authentication.user;

import java.util.Optional;
import jp.bootware.template.springauthbackend.entity.MUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRepository extends CrudRepository<MUserEntity, String> {

  Optional<MUserEntity> findByMailAddress(String email);

  Optional<MUserEntity> findByUserName(String username);

  default MUserEntity findByEmailOrUsername(String emailOrUsername) {

    Optional<MUserEntity> userInfoOpt = null;

    if(emailOrUsername.matches(".+@.+")) {
      userInfoOpt = findByMailAddress(emailOrUsername);
    } else {
      userInfoOpt = findByUserName(emailOrUsername);
    }

    MUserEntity userInfo = userInfoOpt
        .orElseThrow(() -> new UsernameNotFoundException("User not found with userId " + emailOrUsername));

    return userInfo;
  }
}
