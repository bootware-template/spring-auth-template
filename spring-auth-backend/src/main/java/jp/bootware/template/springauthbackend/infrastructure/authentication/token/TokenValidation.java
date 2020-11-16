package jp.bootware.template.springauthbackend.infrastructure.authentication.token;

public interface TokenValidation {

  boolean validate(String token);
}
