package jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.aes;

import jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.CipherProperty;
import jp.bootware.template.springauthbackend.infrastructure.authentication.cipher.CipherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class AESCipherUtilImpl implements CipherUtil {
  
  @Autowired CipherProperty property;

  public SecretKeySpec generateSecretKeySpec() {
    MessageDigest sha;
    try {
      byte[] key = property.getSecureKey().getBytes(StandardCharsets.UTF_8);
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      return new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String encrypt(String str) {

    if (str == null) {
      return null;
    }

    try {
      SecretKeySpec secretKey = generateSecretKeySpec();
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder()
          .encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String decrypt(String str) {

    if (str == null) {
      return null;
    }

    try {
      SecretKeySpec secretKey = generateSecretKeySpec();
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(str)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
