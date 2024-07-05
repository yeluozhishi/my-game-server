package com.whk.service;

import com.whk.centerdb.entity.SysUserEntity;
import com.whk.centerdb.repository.SysUserMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.time.Instant;

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
            sysUserEntity.setCreateTime(Instant.now());
            sysUserEntity.setPassword(password);
            sysUserMapper.save(sysUserEntity);
        }
    }
}
