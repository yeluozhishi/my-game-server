消息传递方式：

client -> gate :
    Message: command + body

服务器之间传递
gate -> game or game -> gate :
    MessageWrapper: Message + int64 playerId

server -> gate: playerId获取user
gate -> server: playerId获取player
client -> gate: userId获取playerId