package com.whk.mongodb.dao;

import com.whk.mongodb.Entity.UserAccount;
import com.whk.mongodb.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountDao extends AbstractDao<UserAccount, String> {

    private UserAccountRepository userAccountRepository;

    @Autowired
    public void setUserAccountRepository(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    protected MongoRepository<UserAccount, String> getMongoRepository() {
        return userAccountRepository;
    }

    @Override
    protected Class<UserAccount> getEntityClass() {
        return UserAccount.class;
    }

    public Optional<UserAccount> findByUserPWD(String userName, String pwd){
        UserAccount userAccount = new UserAccount();
        userAccount.setUser_name(userName);
        userAccount.setPassword(pwd);
        Example<UserAccount> example = Example.of(userAccount);
        return userAccountRepository.findOne(example);
    }

    public Optional<UserAccount> findByOpenId(String openId){
        UserAccount userAccount = new UserAccount();
        userAccount.setUser_name(openId);
        Example<UserAccount> example = Example.of(userAccount);
        return userAccountRepository.findOne(example);
    }

    public List<UserAccount> getAll(){
        return userAccountRepository.findAll();
    }
}
