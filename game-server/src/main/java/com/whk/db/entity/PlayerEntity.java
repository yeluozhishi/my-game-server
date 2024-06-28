package com.whk.db.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "player", schema = "game-server", catalog = "")
public class PlayerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "career", nullable = true)
    private Integer career;
    @Basic
    @Column(name = "sex", nullable = true)
    private Byte sex;
    @Basic
    @Column(name = "last_login", nullable = true)
    private Long lastLogin;
    @Basic
    @Column(name = "coin", nullable = true)
    private Long coin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCoin() {
        return coin;
    }

    public void setCoin(Long coin) {
        this.coin = coin;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PlayerEntity that = (PlayerEntity) object;
        return Objects.equals(id, that.id) && Objects.equals(career, that.career) && Objects.equals(sex, that.sex) && Objects.equals(lastLogin, that.lastLogin) && Objects.equals(coin, that.coin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, career, sex, lastLogin, coin);
    }
}
