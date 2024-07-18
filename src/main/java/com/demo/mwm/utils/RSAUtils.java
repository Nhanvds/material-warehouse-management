package com.demo.mwm.utils;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class RSAUtils {
    private final KeyPair keyPair;

    public RSAUtils(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public String encrypt(String data) {
        try{
            if(data == null){
                throw new IllegalArgumentException("Data is null");
            }
            Cipher cipher = Cipher.getInstance(Constants.Encryption.RSA);
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException ex){
            return Constants.EMPTY_STRING;
        }
    }
    public String decrypt(String encryptedData)  {
        try {
            if(encryptedData == null){
                throw new IllegalArgumentException("Data is null");
            }
            Cipher cipher = Cipher.getInstance(Constants.Encryption.RSA);
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex){
            return Constants.EMPTY_STRING;
        }
    }
}
