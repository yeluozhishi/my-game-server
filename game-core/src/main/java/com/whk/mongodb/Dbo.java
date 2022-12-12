package com.whk.mongodb;

import com.whk.mongodb.dao.UserAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统一使用该工具类，避免多处注入，或注入不生效
 */
@Component
public class Dbo {

    public static UserAccountDao userAccountDao;

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

}
