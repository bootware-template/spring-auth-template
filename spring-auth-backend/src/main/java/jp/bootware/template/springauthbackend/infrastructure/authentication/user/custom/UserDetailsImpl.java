package jp.bootware.template.springauthbackend.infrastructure.authentication.user.custom;

import java.util.Collection;
import java.util.stream.Collectors;
import jp.bootware.template.springauthbackend.entity.MRoleEntity;
import jp.bootware.template.springauthbackend.entity.MUserEntity;
import jp.bootware.template.springauthbackend.entity.TUserRoleEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class UserDetailsImpl implements UserDetails {

  private final MUserEntity user;

  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(MUserEntity user) {
    this.user = user;
    this.authorities = user.getTUserRoles().stream()
        .map(TUserRoleEntity::getMRole)
        .map(MRoleEntity::getRoleName)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.getUnlocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO
    return true;
  }

  @Override
  public boolean isEnabled() {
    return user.getEnabled();
  }
}
