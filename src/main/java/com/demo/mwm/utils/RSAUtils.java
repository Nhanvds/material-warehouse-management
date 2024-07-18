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

    /**
     * Encrypts the provided data using RSA encryption.
     *
     * @param data The plain text data that needs to be encrypted.
     * @return The encrypted data as a Base64-encoded string, or an empty string if an error occurs during encryption.
     * @throws IllegalArgumentException if the provided data is null.
     */
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

    /**
     * Decrypts the provided encrypted data using RSA decryption.
     *
     * @param encryptedData The encrypted data that needs to be decrypted, in Base64-encoded format.
     * @return The decrypted data as a plain text string, or an empty string if an error occurs during decryption.
     * @throws IllegalArgumentException if the provided encryptedData is null.
     */
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
