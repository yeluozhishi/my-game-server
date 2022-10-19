package com.whk.mongodb.Repository;

import com.whk.mongodb.Entity.AdminAccount;
import com.whk.mongodb.Entity.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminAccountRepository extends MongoRepository<AdminAccount, String> {

}
