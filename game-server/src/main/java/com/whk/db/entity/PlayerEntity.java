package com.whk.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/21
 */
@Getter
@Setter
@Entity
@Table(name = "player", schema = "game-server")
public class PlayerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "user_account_id")
    private Long userAccountId;
    @Basic
    @Column(name = "career")
    private Integer career;
    @Basic
    @Column(name = "sex")
    private Byte sex;
    @Basic
    @Column(name = "last_login")
    private Long lastLogin;

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
