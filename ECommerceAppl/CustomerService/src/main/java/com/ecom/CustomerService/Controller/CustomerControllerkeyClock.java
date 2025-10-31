package com.ecom.CustomerService.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerControllerkeyClock {

    @GetMapping("/profile")
    public Map<String, Object> profile(@AuthenticationPrincipal Jwt jwt,
                                       @RequestHeader(value="X-User-Name", required=false) String xuser) {
        Map<String,Object> m = new HashMap<>();
        m.put("user", jwt.getClaimAsString("preferred_username"));
        m.put("roles", jwt.getClaimAsStringList("roles"));
        m.put("x-user-name-header", xuser);
        return m;
    }

    @GetMapping("/admin/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminStats() {
        return "admin stats";
    }
}
