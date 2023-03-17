package com.example.ratelimiter2;

public interface RateLimiterConfig {

    int limitForPeriod();

    int limitPeriodInSeconds();

    String name();
}
