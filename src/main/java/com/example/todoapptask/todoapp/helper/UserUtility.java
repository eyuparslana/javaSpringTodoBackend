package com.example.todoapptask.todoapp.helper;

import java.util.Arrays;
import java.util.Base64;

public final class UserUtility {

    private UserUtility() {
    }

    public static String generateUserToken(String username, String password) {
        String rawToken = String.format("%s:%s", username, password);
        return Base64.getEncoder().encodeToString(rawToken.getBytes());
    }

    public static String getUsernameFromToken(String token) {
        byte[] decodeBytes = Base64.getDecoder().decode(token.split(" ")[1]);
        String decodedToken = new String(decodeBytes);

        return decodedToken.split(":")[0];
    }
}
