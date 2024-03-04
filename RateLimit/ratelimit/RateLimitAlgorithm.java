package ratelimit;

import java.time.Instant;

public interface RateLimitAlgorithm {

    boolean allowRequest(Instant requestTime);
}
