package com.whk.client.net;

import com.whk.util.GsonUtil;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class GameHttpClient {
    private static final Logger logger = Logger.getLogger(GameHttpClient.class.getName());

    // 连接池
    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;

    private static CloseableHttpClient httpClient;

    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslref = new SSLConnectionSocketFactory(builder.build());
            // 配置支持http和https
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslref).build();

            // 初始化连接器
            poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolingHttpClientConnectionManager.setMaxTotal(640);
            httpClient = getConnection();
            logger.info("GameHttpClient 初始化成功");

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static CloseableHttpClient getConnection() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        return HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, false))
                .build();
    }

    public static String post(String uri, Object params, Header... headers){
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse response = null;
        try{
            StringEntity entity = new StringEntity(GsonUtil.INSTANCE.GsonString(params));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            if (headers != null){
                httpPost.setHeaders(headers);
            }

            response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity());
            if (code == HttpStatus.SC_OK){
                return result;
            } else {
                logger.severe("请求" + uri + "返回错误码：" + code + "，请求参数：" + params + "，结果：" + result);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
