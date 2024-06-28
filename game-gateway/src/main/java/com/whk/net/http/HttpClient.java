package com.whk.net.http;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.whk.constant.HttpConstants;
import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.whk.Auth0JwtUtils;
import org.whk.message.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class HttpClient {

    @Getter
    private static HttpClient instance = new HttpClient();

    private HttpClient(){

    }

    private  RestTemplate restTemplate;

    private  String token;

    private  String instanceId;

    public  void setRestTemplate(RestTemplate restTemplate, String instanceId) {
        this.restTemplate = restTemplate;
        this.instanceId = instanceId;
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

    private Object[] postList(String url, ReqMessage message) {
        message.setToke(HttpClient.getInstance().getToken());
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class, message);
        return response.getBody();
    }

    private <T> List<T> postList11(String url, ReqMessage message, Class<T> tClass) {
        message.setToke(HttpClient.getInstance().getToken());
        var res = restTemplate.postForObject(url, message, List.class);
        return (List<T>) res;
    }

    public <T extends ResMessage> List<T> getServerList(ReqMessage message, Class<T> tClass){
        var a = postList(HttpConstants.WEB_CENTER.getInfo() + HttpConstants.SERVER_LIST.getInfo(), message);
        ObjectMapper mapper = new ObjectMapper();
        var res = Arrays.stream(a)
                .map(object -> mapper.convertValue(object, tClass)).collect(Collectors.toList());
        return res;
    }

    public <T> List<T> getPlayerList(ReqPlayerListMessage message, Class<T> tClass){
        return postList11(HttpConstants.WEB_CENTER.getInfo() + HttpConstants.USER_GET_PLAYERS.getInfo(), message, tClass);
    }

    public <T> T createPlayer(ReqCreatePlayerMessage message, Class<T> tClass){
        return post(HttpConstants.WEB_CENTER.getInfo() + HttpConstants.USER_CREATE_PLAYER.getInfo(), message, tClass);
    }


}
