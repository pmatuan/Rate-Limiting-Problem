package ratelimit;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowLog implements RateLimitAlgorithm {

    private final int R;
    private final Queue<Instant> requestTimestamps;

    public SlidingWindowLog(int R) {
        this.R = R;
        this.requestTimestamps = new LinkedList<>();
    }

    public boolean allowRequest(Instant requestTime) {
        while (!requestTimestamps.isEmpty() && requestTimestamps.peek()
            .isBefore(requestTime.minus(1, ChronoUnit.HOURS))) {
            requestTimestamps.poll();
        }

        if (requestTimestamps.size() < R) {
            requestTimestamps.offer(requestTime);
            return true;
        } else {
            return false;
        }
    }

}
