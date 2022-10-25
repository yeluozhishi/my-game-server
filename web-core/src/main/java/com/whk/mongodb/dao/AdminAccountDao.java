package com.whk.mongodb.dao;

import com.whk.mongodb.Entity.AdminAccount;
import com.whk.mongodb.Repository.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminAccountDao extends AbstractDao<AdminAccount, String> {

    private AdminAccountRepository adminAccountRepository;

    @Autowired
    public void setAdminAccountRepository(AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }

    @Override
    public MongoRepository<AdminAccount, String> getMongoRepository() {
        return adminAccountRepository;
    }

    @Override
    protected Class<AdminAccount> getEntityClass() {
        return AdminAccount.class;
    }

    public Optional<AdminAccount> findByUserPWD(String userName, String pwd){
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.setUserName(userName);
        adminAccount.setPassword(pwd);
        Example<AdminAccount> example = Example.of(adminAccount);
        return adminAccountRepository.findOne(example);
    }

    public List<AdminAccount> getAll(){
        return adminAccountRepository.findAll();
    }


}
