package com.demo.mwm.utils;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class AESUtils {
    private final SecretKey secretKey;
    private final GCMParameterSpec gcmParameterSpec;

    public AESUtils(SecretKey secretKey, GCMParameterSpec gcmParameterSpec) {
        this.secretKey = secretKey;
        this.gcmParameterSpec = gcmParameterSpec;
    }

    /**
     * Encrypt the provided encrypted data using AES/GCM/NoPadding.
     * @param data: Data needs to be encrypted
     * @return Data was encrypted, or an empty string if an error occurs during decryption.
     * @throws IllegalArgumentException if the provided encryptedData is null.
     */
    public String encrypt(String data) {
        try {
            if (data == null) {
                throw new IllegalArgumentException("Data is null");
            }
            Cipher cipher = Cipher.getInstance(Constants.Encryption.AES_GCM_NO_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
            byte[] cipherText = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            return Constants.EMPTY_STRING;
        }
    }

    /**
     * Decrypts the provided encrypted data using AES/GCM/NoPadding.
     *
     * @param encryptedData Data that needs to be decrypted, in Base64-encoded format.
     * @return The decrypted data as a plain text string, or an empty string if an error occurs during decryption.
     * @throws IllegalArgumentException if the provided encryptedData is null.
     */
    public String decrypt(String encryptedData) {
        try {
            if (encryptedData == null) {
                throw new IllegalArgumentException("Data is null");
            }
            Cipher cipher = Cipher.getInstance(Constants.Encryption.AES_GCM_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
            byte[] cipherText = Base64.getDecoder().decode(encryptedData);
            byte[] plainText = cipher.doFinal(cipherText);
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            return Constants.EMPTY_STRING;
        }
    }
}
