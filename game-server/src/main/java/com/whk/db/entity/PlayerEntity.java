package com.whk.db.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/21
 */
@Entity
@Table(name = "player", schema = "game-server", catalog = "")
public class PlayerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "user_account_id", nullable = true)
    private Long userAccountId;
    @Basic
    @Column(name = "career", nullable = true)
    private Integer career;
    @Basic
    @Column(name = "sex", nullable = true)
    private Byte sex;
    @Basic
    @Column(name = "last_login", nullable = true)
    private Long lastLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Integer getCareer() {
        return career;
    }

    public void setCareer(Integer career) {
        this.career = career;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerEntity that = (PlayerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userAccountId, that.userAccountId) && Objects.equals(career, that.career) && Objects.equals(sex, that.sex) && Objects.equals(lastLogin, that.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userAccountId, career, sex, lastLogin);
    }
}
