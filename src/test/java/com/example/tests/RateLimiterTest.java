package com.example.tests;


import com.example.ratelimiter.ActualCode;
import com.example.ratelimiter.SimpleTokenBucketRateLimiter;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiterTest {

    SimpleTokenBucketRateLimiter rateLimiter = new SimpleTokenBucketRateLimiter(1000000000, 1);
    ActualCode actualCode = new ActualCode();

    @Test
    public void testThrottleWithTrueResult() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000000000; i++) {
            executorService.execute(() -> {
                boolean isThrottled = rateLimiter.throttle(actualCode);
                Assert.assertTrue(isThrottled);
            });
        }
        executorService.shutdown();
    }

    /*@Test
    public void testThrottleWithFalseResult() {
        boolean isThrottled = rateLimiter.throttle(actualCode);
        Assert.assertFalse(isThrottled);
    }*/
}
