package com.whk.net.http;


import org.springframework.web.client.RestTemplate;
import org.whk.Auth0JwtUtils;

import java.util.Map;

public class HttpClient {

    private static RestTemplate restTemplate;

    private static String token;

    private static String instanceId;

    public static void setRestTemplate(RestTemplate restTemplate, String iId) {
        HttpClient.restTemplate = restTemplate;
        instanceId = iId;
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static String getToken() {
        if (token == null || Auth0JwtUtils.isExpired(token)) {
            token = Auth0JwtUtils.sign(Map.of("instanceId", instanceId));
        }
        return token;
    }
}
