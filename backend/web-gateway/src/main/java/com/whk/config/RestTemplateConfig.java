package com.whk.config;

import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

//@Configuration
public class RestTemplateConfig {

//    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(getFactory());
        JacksonJsonHttpMessageConverter jacksonJsonHttpMessageConverter = new JacksonJsonHttpMessageConverter();
        jacksonJsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM,
                MediaType.TEXT_HTML
        ));
        restTemplate.getMessageConverters().add(jacksonJsonHttpMessageConverter);
        return restTemplate;
    }

    private HttpComponentsClientHttpRequestFactory getFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(3000);
        factory.setReadTimeout(3000);
        return factory;
    }

}
