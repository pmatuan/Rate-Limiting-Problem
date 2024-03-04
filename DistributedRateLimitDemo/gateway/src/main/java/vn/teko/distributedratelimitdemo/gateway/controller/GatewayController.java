package vn.teko.distributedratelimitdemo.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.teko.distributedratelimitdemo.gateway.service.GatewayService;

@RestController
@RequestMapping("/api/v1/hello")
public class GatewayController {

    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        String response = gatewayService.hello();
        if (response.equals("Request rate limit exceeded")) {
            return ResponseEntity.status(429).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
