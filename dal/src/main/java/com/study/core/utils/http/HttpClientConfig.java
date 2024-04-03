package com.study.core.utils.http;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class HttpClientConfig {
    private int maxTotal = 800;

    private int defaultMaxPerRoute = 80;

    private int validateAfterInactivity = 1000;

    /**
     * connectionRequestTimout：指从连接池获取连接的timeout
     */
    private int connectionRequestTimeout = 5000;

    /**
     * connetionTimeout：指客户端和服务器建立连接的timeout
     */
    private int connectTimeout = 30000;

    /**
     * 指客户端从服务器读取数据的timeout
     */
    private int socketTimeout = 60000;

    private int maxIdleTime = 30000;

    private int retryCount = 3;

    @Bean
    public PoolingHttpClientConnectionManager createPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolmanager = new PoolingHttpClientConnectionManager();
        poolmanager.setMaxTotal(maxTotal);
        poolmanager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        poolmanager.setValidateAfterInactivity(validateAfterInactivity);
        return poolmanager;
    }

    @Bean
    public CloseableHttpClient createHttpClient(PoolingHttpClientConnectionManager poolManager) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(poolManager);
        httpClientBuilder.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator iterator = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (iterator.hasNext()) {
                    HeaderElement headerElement = iterator.nextElement();
                    String param = headerElement.getName();
                    String value = headerElement.getValue();
                    if (null != value && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 30 * 1000;
            }
        });
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, false));
        return httpClientBuilder.build();
    }

    @Bean
    public RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
    }

    @Bean
    public IdleConnectionEvictor createIdleConnectionEvictor(PoolingHttpClientConnectionManager poolManager) {
        IdleConnectionEvictor idleConnectionEvictor = new IdleConnectionEvictor(poolManager, maxIdleTime, TimeUnit.MILLISECONDS);
        return idleConnectionEvictor;
    }
}
