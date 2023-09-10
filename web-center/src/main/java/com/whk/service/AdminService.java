package com.whk.service;

import com.whk.db.Entity.SysUserEntity;
import com.whk.db.repository.SysUserMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author Administrator
 */
@Service
public class AdminService {

    private SysUserMapper sysUserMapper;

    @Resource
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }


    public void login(String userName, String password) {
        var sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName(userName);
        sysUserEntity.setPassword(password);
        Example<SysUserEntity> entityExample = Example.of(sysUserEntity);
        var admin = sysUserMapper.findOne(entityExample);
        if (admin.isEmpty()) {
            sysUserEntity = new SysUserEntity();
            sysUserEntity.setUserName(userName);
            sysUserEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            sysUserEntity.setPassword(password);
            sysUserMapper.save(sysUserEntity);
        }
    }
}
