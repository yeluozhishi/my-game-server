消息传递方式：

proto
需要写脚本生成协议号和协议内容对应关系

client <-> gate :
第一种定义Message: 定义command，playerId，字节的payload（荷载），缺点：加、解码执行两次。
第二种，在netty的加解码代码中，加入通过偏移加入协议号
    

服务器之间传递
gate -> game or game -> gate :
    
    Message: 定义command，playerId，字节的payload（荷载），缺点：加、解码执行两次。
    
    RPC：protostuff即使序列化java对象。

