### 脚本引擎
用法：  
        引入该module，创建接口并继承IScript，实现类放在一个新的module中，将实现类所在的module打包jar，向ScriptEngine实例传入jar包位置信息，实现加载。
        ScriptEngine实例的getScript方法传入抽象

原理：  
        key -> instance ,反射注册类实例，放入map，class.getName()对象为key。  
        读取外部jar包实现热更新逻辑代码。  

限制：  
        1. 实现类单独的module。    
        2. 只处理数据，不存放数据。  
        3. 最多作为临时数据对象，进行代理操作。  
        4. 运行中的方法不会被替换下来，运行完后才能用新的方法。故不要有死循环等方式一直持有旧方法。  
        5. 接口和实现类的package保持一致  


功能支持：  
        1. 重载class or method。  
        2. 实现runnable、callable和普通类。
        3. 本地jar包加载
        4. 支持新加或删除方法
        5. 支持新加或删除属性


问题：
        测试
        