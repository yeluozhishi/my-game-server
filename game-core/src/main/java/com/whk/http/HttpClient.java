package com.whk.http;

import org.springframework.web.client.RestTemplate;

public class HttpClient {

    private static RestTemplate restTemplate;

    public static void setRestTemplate(RestTemplate restTemplate) {
        HttpClient.restTemplate = restTemplate;
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
