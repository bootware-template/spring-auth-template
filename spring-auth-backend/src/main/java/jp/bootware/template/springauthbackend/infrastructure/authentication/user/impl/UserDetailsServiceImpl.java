package jp.bootware.template.springauthbackend.infrastructure.authentication.user.impl;

import jp.bootware.template.springauthbackend.entity.MUserEntity;
import jp.bootware.template.springauthbackend.infrastructure.authentication.user.MUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  MUserRepository MUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    MUserEntity user = MUserRepository.findByEmailOrUsername(username);
    return new UserDetailsImpl(user);
  }
}
