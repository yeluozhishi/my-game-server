package com.whk.mongodb.dao;

import com.whk.mongodb.Entity.AdminAccount;
import com.whk.mongodb.Repository.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminAccountDao {

    private AdminAccountRepository adminAccountRepository;

    @Autowired
    public void setAdminAccountRepository(AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }



    public Optional<AdminAccount> findByUserPWD(String userName, String pwd){
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.setUserName(userName);
        adminAccount.setPassword(pwd);
        Example<AdminAccount> example = Example.of(adminAccount);
        return Optional.empty();
    }

    public List<AdminAccount> getAll(){
        return List.of();
    }


}
