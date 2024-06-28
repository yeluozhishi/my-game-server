package org.whk.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class Server extends ResMessage {
    /**
     * 服务器id
     */
    private Integer serverId;

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
    private Date openServerTime;

    /**
     * 开放入口时间
     */
    private Date openEntranceTime;

}
