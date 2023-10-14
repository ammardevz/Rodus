package com.github.ammardevz.rodus;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Cryptography {

    private static final String ALGORITHM = "AES";

    public static String encrypt(String password, String pin, String content) throws Exception {
        SecretKeySpec secretKey = generateSecretKey(password, pin);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(gateMappedBytes(content, password, pin));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String password, String pin, String encryptedContent) throws Exception {
        SecretKeySpec secretKey = generateSecretKey(password, pin);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedContent);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return gateMappedString(new String(decryptedBytes, StandardCharsets.UTF_8), password, pin);
    }

    private static SecretKeySpec generateSecretKey(String password, String pin) throws Exception {
        String reversedPassword = new StringBuilder(password).reverse().toString();
        byte[] key = reversedPassword.getBytes(StandardCharsets.UTF_8);

        // First Hashing Layer
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        key = sha1.digest(key);

        // Second Hashing Layer
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        key = sha256.digest(key);

        // Third Hashing Layer
        MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
        key = sha512.digest(key);

        // Extra Layer using PIN as Byte Mapper
        byte[] pinBytes = pin.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < key.length; i++) {
            key[i] ^= pinBytes[i % pinBytes.length];
        }

        key = Arrays.copyOf(key, 16);

        return new SecretKeySpec(key, ALGORITHM);
    }

    private static byte[] gateMappedBytes(String content, String password, String pin) {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] pinBytes = pin.getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i < contentBytes.length; i++) {
            contentBytes[i] ^= passwordBytes[i % passwordBytes.length];
            contentBytes[i] += (byte) Math.sin(pinBytes[i % pinBytes.length]);
            contentBytes[i] -= (byte) Math.cos(passwordBytes[i % passwordBytes.length]);
        }

        return contentBytes;
    }

    private static String gateMappedString(String content, String password, String pin) {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] pinBytes = pin.getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i < contentBytes.length; i++) {
            contentBytes[i] -= (byte) Math.sin(passwordBytes[i % passwordBytes.length]);
            contentBytes[i] += (byte) Math.cos(pinBytes[i % pinBytes.length]);
            contentBytes[i] ^= passwordBytes[i % passwordBytes.length];
        }

        return new String(contentBytes, StandardCharsets.UTF_8);
    }
}