package com.whk.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.regex.Pattern;

@Configuration
@ConfigurationProperties(prefix = "gateway.filter")
public class FilterConfig {

    /**
     * 请求白名单
     */
    private List<String> whiteRequestUri;

    public List<String> getWhiteRequestUri() {
        return whiteRequestUri;
    }

    public void setWhiteRequestUri(List<String> whiteRequestUri) {
        this.whiteRequestUri = whiteRequestUri.stream().map(this::getRegPath).toList();
    }

    /**
     * 将通配符表达式转化为正则表达式
     *
     * @param path 路径
     */
    private String getRegPath(String path) {
        char[] chars = path.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder();
        boolean preX = false;
        for (int i = 0; i < len; i++) {
            //遇到*字符
            if (chars[i] == '*') {
                //如果是第二次遇到*，则将**替换成.*
                if (preX) {
                    sb.append(".*");
                    preX = false;
                } else if (i + 1 == len) {
                    //如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
                    sb.append("[^/]*");
                } else {
                    //否则单星后面还有字符，则不做任何动作，下一把再做动作
                    preX = true;
                }
            } else {
                //遇到非*字符
                if (preX) {
                    //如果上一把是*，则先把上一把的*对应的[^/]*添进来
                    sb.append("[^/]*");
                    preX = false;
                }
                if (chars[i] == '?') {
                    //接着判断当前字符是不是?，是的话替换成.
                    sb.append('.');
                } else {
                    //不是?的话，则就是普通字符，直接添进来
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 通配符模式
     *
     * @param excludePath - 不过滤地址
     * @param reqUrl      - 请求地址
     */
    private boolean filterUrls(String excludePath, String reqUrl) {
        return Pattern.compile(excludePath).matcher(reqUrl).matches();
    }

    /**
     * 检验是否在非过滤地址
     *
     * @param reqUrl 请求的url
     */
    public boolean checkWhiteList(String reqUrl) {
        for (String url : whiteRequestUri) {
            if (filterUrls(url, reqUrl)) {
                return true;
            }
        }
        return false;
    }
}


