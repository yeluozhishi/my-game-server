package com.whk.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

/**
 * @author wanghongkun
 * @description
 * @date 2023/10/7
 */
@Getter
@Setter
@Entity
@Table(name = "server_info")
public class ServerInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "server_id")
    private Long serverId;

    @Column(name = "server_zone")
    private Integer serverZone;

    @Column(name = "instance_id")
    private String instanceId;

    @Column(name = "server_type")
    private Integer serverType;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "open_server_time")
    private Date openServerTime;

    @Column(name = "open_entrance_time")
    private Date openEntranceTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInfoEntity that = (ServerInfoEntity) o;
        return Objects.equals(serverId, that.serverId) && Objects.equals(serverZone, that.serverZone) && Objects.equals(instanceId, that.instanceId) && Objects.equals(serverType, that.serverType) && Objects.equals(serverName, that.serverName) && Objects.equals(openServerTime, that.openServerTime) && Objects.equals(openEntranceTime, that.openEntranceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, serverZone, instanceId, serverType, serverName, openServerTime, openEntranceTime);
    }
}
