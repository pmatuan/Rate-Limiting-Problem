package vn.teko.distributedratelimitdemo.gateway.ratelimit;

import java.time.Instant;

public interface RateLimitAlgorithm {
    boolean allowRequest(Instant requestTime);
}
