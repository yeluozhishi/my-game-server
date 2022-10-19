## 基本结构

### 流程
通过web_center注册或登录账户 -> 获取token、游戏服网关、游戏服列表 -> 选择游戏服 -> 选择角色\
 -> 进入游戏

### web_center
承担中控\
游戏服配置\
玩家注册登录\
通过eureka获取游戏服网关列表\
均衡负载网关

### game_server
游戏业务