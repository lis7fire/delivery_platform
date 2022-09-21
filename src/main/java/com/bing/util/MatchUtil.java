package com.bing.util;

import org.springframework.util.AntPathMatcher;

public class MatchUtil {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public static boolean check(String uri, String[] ignoreUrls) {

        for (String ignoreUrl : ignoreUrls) {
            if (PATH_MATCHER.match(ignoreUrl, uri))
                return true;
        }
        // 未在列表中
        return false;
    }

}
