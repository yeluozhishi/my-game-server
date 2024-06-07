消息传递方式：

client -> gate :
    Message: command + body

gate -> game or game -> gate :
    MessageWrapper: Message + string serverInstance + int64 playerId  +  int64 userId

