package com.example;

import com.example.ratelimiter.ActualCode;
import com.example.ratelimiter.RateLimiter;
import com.example.ratelimiter.SimpleTokenBucketRateLimiter;

public class Runner {
    public static void main(String[] args) {
        RateLimiter limiter = new SimpleTokenBucketRateLimiter(10, 5);
        ActualCode actualCode = new ActualCode();
        Thread[] group = new Thread[16];
        Runnable r = () -> {
            for (int i = 0; i < 50; i++) {
                try {
                    if (limiter.throttle(actualCode)) {
                        System.out.println("Values:- " + Thread.currentThread().getName() + ": " + i);
                    }
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        for (int i = 0; i < 4; i++) {
            group[i] = new Thread(r);
            group[i].start();
        }
    }
}

