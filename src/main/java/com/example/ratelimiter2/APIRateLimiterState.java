package com.example.ratelimiter2;

public class APIRateLimiterState {
    RateLimiterConfig rateLimiterConfig;
    private long startTime = 0L;
    private int availableTokens;

    APIRateLimiterState(RateLimiterConfig rateLimiterConfig) {
        this.rateLimiterConfig = rateLimiterConfig;
        this.availableTokens = rateLimiterConfig.limitForPeriod();
    }

    public RateLimiterConfig getRateLimiterConfig() {
        return rateLimiterConfig;
    }

    public void setRateLimiterConfig(RateLimiterConfig rateLimiterConfig) {
        this.rateLimiterConfig = rateLimiterConfig;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getAvailableTokens() {
        return availableTokens;
    }

    public void setAvailableTokens(int availableTokens) {
        this.availableTokens = availableTokens;
    }
}
