package com.demo.mwm.config;

import com.demo.mwm.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class AESConfig {

    @Bean
    public SecretKey aesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Constants.Encryption.AES);
        keyGenerator.init(Constants.Encryption.AES_KEY_LENGTH);
        return keyGenerator.generateKey();
    }

    @Bean
    public GCMParameterSpec gcmParameterSpec() {
        byte[] iv = new byte[Constants.Encryption.GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return new GCMParameterSpec(Constants.Encryption.GCM_TAG_LENGTH_BITS, iv);
    }
}
