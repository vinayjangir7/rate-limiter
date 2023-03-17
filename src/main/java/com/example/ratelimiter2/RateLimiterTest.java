package com.example.ratelimiter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiterTest {

    static RateLimiter rateLimiter = new RateLimiter();

    static class DoSomethingRateLimiterConfig implements RateLimiterConfig {

        String name;
        int limit;
        int durationInSeconds;

        DoSomethingRateLimiterConfig(String name, int maxRequest, int durationInSeconds) {
            this.name = name;
            this.limit = maxRequest;
            this.durationInSeconds = durationInSeconds;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public int limitForPeriod() {
            return limit;
        }

        @Override
        public int limitPeriodInSeconds() {
            return durationInSeconds;
        }

    }

    public static void isRateLimited() {
        DoSomethingRateLimiterConfig doSomethingRateLimiter = new DoSomethingRateLimiterConfig("doSomething", 2, 1);
        rateLimiter.configure(doSomethingRateLimiter);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    doSomething(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    private static void doSomething(int i) throws InterruptedException {
        if (!rateLimiter.isRateLimited("doSomething")) {
            Thread.sleep(1000);
            System.out.println("Processing {}" + i);
        } else {
            System.out.println("{} This is rate limited" + i);
        }
    }

    public static void main(String[] args) {
        isRateLimited();
    }

}
