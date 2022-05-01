package com.whk.mongodb.Repository;

import com.whk.mongodb.Entity.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

}
