package com.website.website.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BcryptHasher {

    private final int logRounds;
    public BcryptHasher() {
        this.logRounds = 6;
    }
    public BcryptHasher(int logRounds) {
        this.logRounds = logRounds;
    }
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public static void main(String[] args) {
        com.website.website.utils.BcryptHasher bcryptHasher = new com.website.website.utils.BcryptHasher();
        System.out.println(bcryptHasher.hash("Welcome"));

    }
}