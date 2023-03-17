package com.example.ratelimiter;


/**
 * Interface to represent a contiguous piece of code that needs to be rate limited.
 */
interface Code {
    /**
     * Calling this function should execute the code that is delegated to this interface.
     */
    void invoke();
}