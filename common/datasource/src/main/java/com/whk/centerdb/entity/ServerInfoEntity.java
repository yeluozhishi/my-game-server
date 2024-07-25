package com.whk.centerdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "server_info", schema = "admin")
public class ServerInfoEntity {
    @Id
    @Column(name = "server_id", nullable = false)
    private Integer id;

    @Column(name = "server_zone")
    private Integer serverZone;

    @Column(name = "server_type")
    private Integer serverType;

    @Size(max = 255)
    @Column(name = "server_name")
    private String serverName;

    @Column(name = "open_server_time")
    private Instant openServerTime;

    @Column(name = "open_entrance_time")
    private Instant openEntranceTime;

}