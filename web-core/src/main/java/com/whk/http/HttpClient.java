package com.whk.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.whk.Auth0JwtUtils;
import com.whk.TimeUtils;
import com.whk.message.ReqMessage;
import com.whk.message.ResMessage;
import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.whk.http.HttpConstants.GAME_GATEWAY;
import static com.whk.http.HttpConstants.UPDATE_SERVERS;

public class HttpClient {

    @Getter
    private static HttpClient instance = new HttpClient();

    private HttpClient() {

    }

    private RestTemplate restTemplate;

    private String token;

    private String instanceId;

    private ObjectMapper mapper;

    public void setRestTemplate(RestTemplate restTemplate, String instanceId) {
        this.restTemplate = restTemplate;
        this.instanceId = instanceId;
        this.mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(TimeUtils.YYYY_MM_DD_T__HH_MM_SS)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(TimeUtils.YYYY_MM_DD_T__HH_MM_SS)));
        mapper.registerModule(javaTimeModule);
    }

    public String getToken() {
        if (token == null || Auth0JwtUtils.isExpired(token)) {
            token = Auth0JwtUtils.sign(Map.of("instanceId", instanceId));
        }
        return token;
    }

    public <T> T post(String url, ReqMessage message, Class<T> tClass) {
        message.setToke(HttpClient.getInstance().getToken());
        return restTemplate.postForObject(url, message, tClass);
    }

    public <T extends ResMessage> List<T> getProjectFileList(String url, ReqMessage message, Class<T> tClass) {
        message.setToke(HttpClient.getInstance().getToken());
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);

        HttpEntity<ReqMessage> httpEntity = new HttpEntity<>(message, headers);
        ResponseEntity<List<Object>> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
        });
        return Objects.requireNonNull(response.getBody()).stream()
                .map(object -> mapper.convertValue(object, tClass)).collect(Collectors.toList());
    }

    public <T> T noticeGateServer(ReqMessage message, Class<T> tClass){
        return post(GAME_GATEWAY.getInfo() + UPDATE_SERVERS.getInfo(), message, tClass);
    }
}