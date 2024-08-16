### skill

    成员：config, finite state machine, source, target
    skill stage: 读取配置，将每个阶段组成链表，该阶段完成进入下一阶，直到完成。

### 过程
    1. 组装成员
    2. 交给map线程
    3. 状态变更至结束状态，丢弃

### skill stage
    条件：存在时间，触发完成条件，