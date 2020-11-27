package jp.bootware.template.springauthbackend.infrastructure.authentication.token.jwt;

import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenUtil;
import jp.bootware.template.springauthbackend.infrastructure.authentication.token.TokenValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JSONWebTokenAuthFilter extends OncePerRequestFilter {
  
  @Autowired
  TokenUtil tokenUtil;
  @Autowired
  TokenValidation validation;
  @Autowired
  UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {

    try {

      String jwt = ((JSONWebTokenUtilImpl) tokenUtil).getJwtFromCookie(httpServletRequest);

      if (StringUtils.hasText(jwt) && validation.validate(jwt)) {

        String username = tokenUtil.getUsernameFromToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        authentication
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

    } catch (Exception ex) {
      log.warn("Token Filter Error : {}", ex);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

}
