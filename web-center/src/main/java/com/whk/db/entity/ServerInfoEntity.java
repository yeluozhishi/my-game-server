package com.whk.db.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author wanghongkun
 * @description
 * @date 2023/10/7
 */
@Entity
@Table(name = "server_info", schema = "admin", catalog = "")
public class ServerInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "zone")
    private Integer zone;
    @Basic
    @Column(name = "instance_id")
    private String instanceId;
    @Basic
    @Column(name = "server_type")
    private Integer serverType;
    @Basic
    @Column(name = "server_name")
    private String serverName;
    @Basic
    @Column(name = "open_server_time")
    private Timestamp openServerTime;
    @Basic
    @Column(name = "open_entrance_time")
    private Timestamp openEntranceTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Timestamp getOpenServerTime() {
        return openServerTime;
    }

    public void setOpenServerTime(Timestamp openServerTime) {
        this.openServerTime = openServerTime;
    }

    public Timestamp getOpenEntranceTime() {
        return openEntranceTime;
    }

    public void setOpenEntranceTime(Timestamp openEntranceTime) {
        this.openEntranceTime = openEntranceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInfoEntity that = (ServerInfoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(zone, that.zone) && Objects.equals(instanceId, that.instanceId) && Objects.equals(serverType, that.serverType) && Objects.equals(serverName, that.serverName) && Objects.equals(openServerTime, that.openServerTime) && Objects.equals(openEntranceTime, that.openEntranceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zone, instanceId, serverType, serverName, openServerTime, openEntranceTime);
    }
}
