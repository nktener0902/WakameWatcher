package com.wakame.observer.raspberry.domain.messaging;

public class Token {

    private String token;

    public static Token createToken(String token) {
        return new Token(token);
    }

    private Token(String token){
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }
}
