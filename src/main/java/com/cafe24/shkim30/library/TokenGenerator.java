// src/main/java/com/cafe24/token/TokenGenerator.java
package com.cafe24.shkim30.library;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * Node.js validateToken 함수와 호환되는 토큰을 생성해 줍니다.
 */
public class TokenGenerator {

    private static final String HMAC_ALGO = "HmacSHA256";

    /**
     * 서버 비밀 키입니다. Node.js 의 SERVER_SECRET 값과 동일하게 설정하세요.
     */
    private final String secret;

    public TokenGenerator(String secret) {
        this.secret = secret;
    }

    /**
     * 만료 시간을 직접 지정하여 토큰을 생성합니다.
     *
     * @param expiryTimestamp 토큰 만료 시각 (epoch 초)
     * @return signature.expiryTimestamp
     */
    public String generateToken(long expiryTimestamp) throws Exception {
        try {
            String data = Long.toString(expiryTimestamp);
            String sig = hmacSha256(data, secret);
            return sig + "." + data;
        } catch (Exception e) {
            throw new Exception("토큰 생성 실패", e);
        }
    }

    /**
     * 현재 시각으로부터 validitySeconds 이후를 만료 시간으로 하여 토큰을 생성합니다.
     *
     * @param validitySeconds 토큰 유효 기간(초)
     * @return signature.expiryTimestamp
     */
    public String generateTokenWithValidity(long validitySeconds) throws Exception {
        long expiry = Instant.now().getEpochSecond() + validitySeconds;
        return generateToken(expiry);
    }

    private String hmacSha256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
        mac.init(spec);
        byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(raw);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
