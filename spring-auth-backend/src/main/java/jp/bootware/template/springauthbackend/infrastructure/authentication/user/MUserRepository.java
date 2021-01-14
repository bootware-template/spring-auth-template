package jp.bootware.template.springauthbackend.infrastructure.authentication.user;

import jp.bootware.template.springauthbackend.entity.MUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface MUserRepository extends CrudRepository<MUserEntity, String> {

  Optional<MUserEntity> findByMailAddress(String email);

  Optional<MUserEntity> findByUserName(String username);

  default MUserEntity findByEmailOrUsername(String emailOrUsername) {

    Optional<MUserEntity> userInfoOpt = null;

    if (emailOrUsername.matches(".+@.+")) {
      userInfoOpt = findByMailAddress(emailOrUsername);
    } else {
      userInfoOpt = findByUserName(emailOrUsername);
    }

    MUserEntity userInfo = userInfoOpt
        .orElseThrow(() -> new UsernameNotFoundException("User not found with userId " + emailOrUsername));

    return userInfo;
  }
}
