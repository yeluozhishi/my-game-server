package com.whk.filter;

import com.whk.util.Auth0JwtUtils;
import com.whk.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.whk.message.MapBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class TokenVerifyFilter implements GlobalFilter, GatewayFilter, Ordered {

    private FilterConfig filterConfig;

    private static final Logger logger = Logger.getLogger(TokenVerifyFilter.class.getName());

    @Autowired
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUri = exchange.getRequest().getURI().getPath();

        if (filterConfig.checkWhiteList(requestUri)) {
            // 放行白名单路径
            return chain.filter(exchange);
        }

        // 获取cookies数据
        var cookie = exchange.getRequest().getHeaders().get("token");
        if (cookie != null && !cookie.isEmpty() && Auth0JwtUtils.verify(cookie.stream().findFirst().get())) {
            return chain.filter(exchange);
        }
        // 对比token
        long contentLength = exchange.getRequest().getHeaders().getContentLength();
        if (contentLength > 0) {
            return readBody(exchange, chain);
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    /**
     * default HttpMessageReader
     */
    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

    /**
     * ReadJsonBody
     * webflux是非阻塞线程，即body.subscribe内还没有执行完，你已经使用了bodyString.get，导致bodyString获取为null
     *
     * @param exchange request-response interaction
     * @param chain
     */
    private Mono<Void> readBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        // join the body
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                DataBufferUtils.retain(buffer);
                return Mono.just(buffer);
            });
            // repackage ServerHttpRequest
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            // mutate exchage with new ServerHttpRequest
            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            // read body string with default messageReaders
            return ServerRequest.create(mutatedExchange, MESSAGE_READERS).bodyToMono(String.class)
                    .doOnNext(objectValue -> {
                        logger.info(String.valueOf(Map.of("objectValue", objectValue)));

                        String token = (String)GsonUtil.INSTANCE.GsonToBean(objectValue, MapBean.class).get("token");

                        if (!StringUtils.hasLength(token)) {
                            logger.warning("token void");
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        }

                        if (token == null || !Auth0JwtUtils.verify(token)) {
                            // 设置401
                            logger.warning("token verify fails");
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        }
                    }).then(chain.filter(mutatedExchange));
        });
    }


}
