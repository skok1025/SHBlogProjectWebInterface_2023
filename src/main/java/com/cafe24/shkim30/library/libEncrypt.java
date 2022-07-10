package com.cafe24.shkim30.library;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * 암호화 라이브러리
 */
public class libEncrypt {

    public static final String AES_KEY = "abcdefghijklmnop";

    public static String getSHA512(String input){

        String toReturn = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public static String encrypt_AES(byte[] target, byte[] key){
        SecretKeySpec keySpec = null;

        keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        } catch (InvalidKeyException e) {
            return null;
        }

        try {
            Encoder encoder = Base64.getEncoder();
            return new String(encoder.encode(cipher.doFinal(target)));
        } catch (IllegalBlockSizeException e) {

        } catch (BadPaddingException e) {

        }
        return null;
    }

    public static String decrypt_AES(byte[] target, byte[] key){
        SecretKeySpec keySpec = null;

        keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {

            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        } catch (InvalidKeyException e) {

            return null;
        }

        try {
            Decoder encoder = Base64.getDecoder();
            return new String(cipher.doFinal(encoder.decode(target)));
        } catch (IllegalBlockSizeException e) {
            System.out.println("err");
        } catch (BadPaddingException e) {

        }
        return null;
    }
}
