### 脚本引擎
用法：  
        接口继承IScript，实现类放在一个新的module中，将实现类所在的module打包出jar，向ScriptEngine实例传入jar包位置信息，实现加载。

原理：  
        key -> instance ,反射注册类实例，放入map，class.getName()对象为key。  
        读取外部jar包实现热更新逻辑代码。  

限制：  
        1. 重载其他jar中的class资源。  
        2. module不同。  
        3. 只处理数据，不存放数据。


功能支持：  
        1. 重载class or method。  
        2. 实现runnable、callable和普通类。
        3. 本地jar包加载


问题：
        实现
        