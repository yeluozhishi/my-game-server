### java 脚本引擎

用法：

    1. server引入该module，创建接口并继承IScript。
    2. 创建一个新的模块（例如名为script的模块）,script引入server模块，实现类放在一个script中并给类放上注解@Script。
    3. 将实现类所在的module打包jar，
    4. 向ScriptEngine实例传入jar包位置信息，实现加载。
    5. ScriptEngine实例的getScript方法传入接口类，即可调用所需方法。

    开发模式下导致server不包含script，需要改到在script下启动解决。

原理：

    key -> instance ,反射注册类实例，放入map，class.getName()对象为key。  
    读取外部jar包实现热更新逻辑代码。  

限制：

    1. 实现类放单独的module。    
    2. 只处理数据，不存放数据。  
    3. 最多作为临时数据对象，进行代理操作。  
    4. 运行中的方法不会被替换下来，运行完后才能用新的方法。故不要有死循环等方式一直持有旧方法。  

功能支持：

    1. 重载class or method。  
    2. 实现runnable、callable和普通类。
    3. 本地jar包加载
    4. 支持新加或删除方法
    5. 支持新加或删除属性

问题：
        