package com.whk.message;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class Server extends ResMessage {
    /**
     * 服务器id
     */
    private int id;

    /**
     * 大区
     */
    private Integer serverZone;

    /**
     * 服务器实例id
     */
    private String instanceId;

    /**
     * 类型
     */
    private Integer serverType;

    /**
     * 服务器名
     */
    private String serverName;
    /**
     * 开服时间
     */
    private LocalDateTime openServerTime;

    /**
     * 开放入口时间
     */
    private LocalDateTime openEntranceTime;

}
