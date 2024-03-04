package vn.teko.distributedratelimitdemo.gateway.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SlidingWindowLog implements RateLimitAlgorithm {

    @Value("${rate-limit.allowed-requests-per-second}")
    private int allowedRequestsPerSecond;

    @Autowired
    private RedisTemplate<String, Instant> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean allowRequest(Instant requestTime) {
        String key = "requests";
        RLock lock = redissonClient.getLock(key);

        try {
            boolean acquired = lock.tryLock(1, TimeUnit.SECONDS);
            if (!acquired) {
                log.error("Failed to acquire lock for key {}", key);
                return true; // If have lock error, allow the request
            }

            Instant oldestAllowedRequestTime = requestTime.minus(Duration.ofSeconds(1));
            redisTemplate.opsForZSet().removeRangeByScore(key, Double.MIN_VALUE, oldestAllowedRequestTime.toEpochMilli());
            Set<Instant> requestsInWindow = redisTemplate.opsForZSet().rangeByScore(key, oldestAllowedRequestTime.toEpochMilli(), Double.MAX_VALUE);
            int requestsCount = requestsInWindow.size();

            if (requestsCount >= allowedRequestsPerSecond) {
                return false;
            }

            redisTemplate.opsForZSet().add(key, requestTime, requestTime.toEpochMilli());
            return true;
        }
        catch (Exception e) {
            log.error("Error while processing request", e);
            return true; // If RateLimit service is down, allow the request
        }
        finally {
            lock.unlock();
        }
    }
}

