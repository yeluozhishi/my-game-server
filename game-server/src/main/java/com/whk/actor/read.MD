### Actor设计
    base component: movement,attributes,statuses,besicInfo,behavior

### Player extends Actor
    base component: modules,resource,bag,repository

### Monster extends Actor
    base component: monster config


### 数据存储
    方式1：按照组件来保存，将组件直接序列化保存
        优点：类似MongoDB，加减字段不需要改表，全量保存。不新加数据库相关代码。工作量小。
        缺点：每次更新数据大。没有可读性(转json、数据加载、换MongoDB等方式解决)。
    方式2：根据需求创建表和字段
        优点：更新数据小。有可读性。
        缺点：变动要改表，新加数据库相关代码。工作量大。登陆时大量查询。

    简单原则: 方式1，protostuff序列化。