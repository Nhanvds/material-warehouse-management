package com.demo.mwm.config;


import com.demo.mwm.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Configuration
@Scope(value = "singleton")
public class RSAConfig {

    @Bean
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Constants.Encryption.RSA);
        keyPairGenerator.initialize(Constants.Encryption.RSA_KEY_LENGTH);
        return keyPairGenerator.generateKeyPair();
    }
}
