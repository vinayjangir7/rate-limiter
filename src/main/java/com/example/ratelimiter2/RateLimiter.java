package com.example.ratelimiter2;

import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

    private final Map<String, APIRateLimiterState> registeredAPIs;

    RateLimiter() {
        registeredAPIs = new HashMap<>();
    }

    void configure(RateLimiterConfig rateLimiterConfig) {
        // TO DO : do validation
        registeredAPIs.put(rateLimiterConfig.name(), new APIRateLimiterState(rateLimiterConfig));
    }

    synchronized boolean isRateLimited(String name) {

        if (registeredAPIs.containsKey(name)) {
            APIRateLimiterState apiRateLimiterState = registeredAPIs.get(name);
            long currentTime = System.currentTimeMillis();
            long timeElapsedSinceBucketStart = currentTime - apiRateLimiterState.getStartTime();
            System.out.printf("Available Tokens = {%d}, Elapsed = {%d} ms%n", apiRateLimiterState.getAvailableTokens() , timeElapsedSinceBucketStart);
            if (apiRateLimiterState.getStartTime() == 0L) {
                System.out.println("First API call. Setting the start time to current time, decreasing the available token count by 1 and allowing the request");
                apiRateLimiterState.setStartTime(currentTime);
                apiRateLimiterState.setAvailableTokens(apiRateLimiterState.getAvailableTokens() - 1);
                return false;
            } else if (timeElapsedSinceBucketStart >= apiRateLimiterState.rateLimiterConfig.limitPeriodInSeconds() * 1000L) {
                System.out.println("Rate Limiting Period Time Elapsed. Resetting the start time to current time and token count to original token count -1 and allowing the request");
                apiRateLimiterState.setStartTime(currentTime);
                apiRateLimiterState.setAvailableTokens(apiRateLimiterState.getRateLimiterConfig().limitForPeriod() - 1);
                return false;
            } else if (timeElapsedSinceBucketStart < apiRateLimiterState.rateLimiterConfig.limitPeriodInSeconds() * 1000L
                    && apiRateLimiterState.getAvailableTokens() >= 1) {
                System.out.println("Rate limit not reached, decreasing the available token counts by 1 and allowing the request");
                apiRateLimiterState.setAvailableTokens(apiRateLimiterState.getAvailableTokens() - 1);
                return false;
            } else {
                System.out.println("Rate Limit threshold reached");
                return true;
            }
        } else {
            System.out.println("Rate limit not configured for the endpoint {}" + name);
            return false;
        }
    }
}
