package com.whk.util;

import org.springframework.context.ApplicationContext;

public class SpringUtil {
    private static ApplicationContext appContext;

    public static void setAppContext(ApplicationContext appContext) {
        SpringUtil.appContext = appContext;
    }

    public static ApplicationContext getAppContext() {
        return appContext;
    }
}
