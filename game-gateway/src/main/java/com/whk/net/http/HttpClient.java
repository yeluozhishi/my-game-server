package com.whk.net.http;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.whk.DateUtils;
import com.whk.constant.HttpConstants;
import com.whk.message.*;
import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.whk.Auth0JwtUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class HttpClient {

    @Getter
    private static HttpClient instance = new HttpClient();

    private HttpClient(){

    }

    private  RestTemplate restTemplate;

    private  String token;

    private  String instanceId;

    private ObjectMapper mapper;

    public  void setRestTemplate(RestTemplate restTemplate, String instanceId) {
        this.restTemplate = restTemplate;
        this.instanceId = instanceId;
        this.mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateUtils.YYYY_MM_DD_T__HH_MM_SS)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateUtils.YYYY_MM_DD_T__HH_MM_SS)));
        mapper.registerModule(javaTimeModule);
    }

    public String getToken() {
        if (token == null || Auth0JwtUtils.isExpired(token)) {
            token = Auth0JwtUtils.sign(Map.of("instanceId", instanceId));
        }
        return token;
    }

    private <T> T post(String url, ReqMessage message, Class<T> tClass) {
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

    public List<Server> getServerList(ReqMessage message){
        return getProjectFileList(HttpConstants.WEB_CENTER.getInfo() + HttpConstants.SERVER_LIST.getInfo(), message, Server.class);
    }

    public List<PlayerEntityMessage> getPlayerList(ReqPlayerListMessage message){
        return getProjectFileList(HttpConstants.WEB_CENTER.getInfo() + HttpConstants.USER_GET_PLAYERS.getInfo(), message, PlayerEntityMessage.class);
    }

    public <T> T createPlayer(ReqCreatePlayerMessage message, Class<T> tClass){
        return post(HttpConstants.WEB_CENTER.getInfo() + HttpConstants.USER_CREATE_PLAYER.getInfo(), message, tClass);
    }


}
