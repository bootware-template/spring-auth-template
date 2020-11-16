package jp.bootware.template.springauthbackend.infrastructure.authentication.user.custom;

import jp.bootware.template.springauthbackend.entity.MUserEntity;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    MUserEntity user = userRepository.findByEmailOrUsername(username);
    return new UserDetailsImpl(user);
  }
}
