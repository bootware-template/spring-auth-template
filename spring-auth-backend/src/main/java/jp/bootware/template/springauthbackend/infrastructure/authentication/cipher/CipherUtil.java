package jp.bootware.template.springauthbackend.infrastructure.authentication.cipher;

import javax.crypto.spec.SecretKeySpec;

public interface CipherUtil {

  SecretKeySpec generateSecretKeySpec();

  String encrypt(String str);

  String decrypt(String str);
}
