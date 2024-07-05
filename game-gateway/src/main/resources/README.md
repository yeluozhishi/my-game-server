### serverId、zone设定
kafka:  
    groupId:同一topic下，同一groupId下一台机器接收， 不同groupId都接收。 不变，除非有必要。  
    topic:固定前缀-zone-serverId

server:  
    zone:不变,由控制台管理  
    serverId:递增

eureka:  
    instanceId:固定前缀-zone-serverId  