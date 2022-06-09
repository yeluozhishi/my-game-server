package com.whk.filter;

import com.whk.Exception.GameErrorException;
import com.whk.network_param.WebCenterError;
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
        // 对比请求路径
        List<String> whiteRequestUri = filterConfig.getWhiteRequestUri();
        if (whiteRequestUri.contains(requestUri)) {
            // 放行白名单路径
            return chain.filter(exchange);
        }

        // 获取表单数据
//        String token = exchange.getRequest().getQueryParams().getFirst("token");
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
    private static final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();
    /**
     * ReadJsonBody
     * webflux是非阻塞线程，即body.subscribe内还没有执行完，你已经使用了bodyString.get，导致bodyString获取为null
     *
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> readBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * join the body
         */
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                DataBufferUtils.retain(buffer);
                return Mono.just(buffer);
            });
            /**
             * repackage ServerHttpRequest
             */
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            /*
              mutate exchage with new ServerHttpRequest
             */
            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            /*
              read body string with default messageReaders
             */
            return ServerRequest.create(mutatedExchange, messageReaders).bodyToMono(String.class)
                    .doOnNext(objectValue -> {
                        logger.info(String.valueOf(Map.of("objectValue", objectValue)));

                        Object t = GsonUtil.INSTANCE.GsonToBean(objectValue, Map.class).get("token");
                        if (t == null){
                            throw new GameErrorException(WebCenterError.TOKEN_VOID, "token void");
                        }

                        String token = t.toString();

                        if (!StringUtils.hasLength(token)) {
                            // 设置401
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            throw new GameErrorException(WebCenterError.TOKEN_VOID, "token void");
                        }

                        if (!Auth0JwtUtils.verify(token)) {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            throw new GameErrorException(WebCenterError.TOKEN_FAILED, "token error");
                        }
                    }).then(chain.filter(mutatedExchange));
        });
    }


}
