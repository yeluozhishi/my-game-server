package com.whk.db.repository;

import com.whk.db.entity.SysUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author wanghongkun
 * @description
 * @date 2023/7/27
 */
@Repository
public interface SysUserMapper extends CrudRepository<SysUserEntity, Long> {


}
