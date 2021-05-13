package com.example.demo.util;

import lombok.Getter;

import java.math.BigInteger;
import java.security.MessageDigest;

@Getter
public class SHA512 {

    private String sha512;

    public SHA512(String input) {
        this.sha512 = getSha512(input);
    }

    public String getSha512(String input) {

        String sha512 = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            sha512 = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sha512;
    }
}
