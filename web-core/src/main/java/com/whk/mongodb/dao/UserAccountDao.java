package com.whk.mongodb.dao;

import com.whk.mongodb.Entity.UserAccount;
import com.whk.mongodb.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountDao {

    private UserAccountRepository userAccountRepository;

    @Autowired
    public void setUserAccountRepository(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public Optional<UserAccount> findByUserPWD(String userName, String pwd){
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName(userName);
        userAccount.setPassword(pwd);
        Example<UserAccount> example = Example.of(userAccount);
        return Optional.empty();
    }

    public Optional<UserAccount> findByOpenId(String openId){
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName(openId);
        Example<UserAccount> example = Example.of(userAccount);
        return Optional.empty();
    }

    public List<UserAccount> getAll(){
        return List.of();
    }
}
