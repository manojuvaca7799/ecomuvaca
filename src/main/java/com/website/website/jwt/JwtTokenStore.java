package com.website.website.jwt;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dineshk on 9/25/19.
 */
public class JwtTokenStore {

    private static Set<String> cachedTokens = new HashSet<>();

    public static void addToken(String token) {
        cachedTokens.add(token);
    }
    public static void remove(String token) {
        cachedTokens.remove(token);
    }

    public static boolean isExists(String token) {
        return cachedTokens.contains(token);
    }

}
