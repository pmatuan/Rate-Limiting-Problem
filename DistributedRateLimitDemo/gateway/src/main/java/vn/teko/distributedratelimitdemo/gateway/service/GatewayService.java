package vn.teko.distributedratelimitdemo.gateway.service;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.teko.distributedratelimitdemo.gateway.ratelimit.RateLimitAlgorithm;

@Service
@Slf4j
public class GatewayService {

    @Value("${rate-limit.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final RateLimitAlgorithm rateLimitAlgorithm;

    public GatewayService(RestTemplate restTemplate, RateLimitAlgorithm rateLimitAlgorithm) {
        this.restTemplate = restTemplate;
        this.rateLimitAlgorithm = rateLimitAlgorithm;
    }

    public String hello() {
        log.info("Received request to /api/v1/hello");
        if (!rateLimitAlgorithm.allowRequest(Instant.now())) {
            log.info("Request rate limit exceeded");
            return "Request rate limit exceeded";
        }
        log.info("Forwarding request to API");
        return restTemplate.getForObject(apiUrl + "/api/v1/hello", String.class);
    }
}
