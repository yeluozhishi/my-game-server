### 执行器
    执行器 = 线程池（复用） + 自定义queue（新建执行器时创建，放入Runnable的实现对象）  
    描述：同一类（比如player）使用同一种执行器，将对象或操作封装Runnable子类，存入队列。因为queue不同，对于一个player来说是操作是顺序执行的。  
    
    问题：不同player对同一player操作（战斗）是否会有线程问题？
    fight逻辑：消息给到场景执行器  


### 消息和处理器
    netty下消息处理：使用LengthFieldBasedFrameDecoder来进行消息体的读取和写入。
    消息体：使用protobuf。内容为编码方法、解码方法、消息id、proto对象，length。
    处理器：继承Runnable，实现run()。开放出子类在run中执行的方法（业务处理逻辑）。内容为消息体、线程池id
    
    MsgAndHandlerPool implements MessagePool, HandlerPool  
    消息池：消息id -> 消息体class  
    netty的Decoder和Encoder引用消息池  
    // Decoder示例  
    // 消息体长度  
    int length = frame.readInt();  
    // 消息id  
    int id = frame.readInt();  
    // 获取消息体（新建）  
    Message msg = MessagePool.getMessage(id)  
    msg.decode(bytes);  
    处理器池：消息id -> 处理器class   
    驱动器池：playerId -> 驱动器  
    处理机：处理器池、驱动器池。处理消息过程为，传入消息体，根据消息id取处理器（新建），处理器设置消息体，根据playerId取驱动器（没有就新建并加入驱动器池），驱动器将处理器加入队列并执行。不同消息来源创建不同的处理机去处理消息。
    处理机优化：获取驱动器时，传入playerId和线程池id，新建时选择对应的线程池绑定。目的：玩家自身操作->玩家线程，技能释放->场景线程，选择对应的线程。  


### 问题：  
    hash次数 ： 处理机2 + Decoder1
    
    
    ThreadPoolExecutor.submit(Runnable task)  
    将task封装到FutureTask，task成为callable，执行完后清除了。  
    怎么放入下一个任务的引用？使用execute() 方法。


### db线程池
    如果只是玩家自己保存数据，可以保证顺序性。当跨线程时，得保证数据的原子性，那么保存的数据便是正常的。
    既然与玩家相关便交由玩家驱动器处理，没有驱动器再交由通用的驱动器处理。