package com.example.ratelimiter;

public class ActualCode implements Code {
    @Override
    public void invoke() {
        System.out.println("Code is executing");
    }
}
