### 脚本引擎
原理：
        key -> instance ,反射注册类实例，放入map，class.getName()对象为key。
限制：
        1. 重载其他jar中的class资源。本包无法重载？  
        2. module不同，相对路径相同  
        

功能支持：
        1. 重载class or method。  
        2. 只处理数据，不存放数据。  
        3. 实现runnable、callable和普通类。
        4. spring、jar

问题：
        重新读入后没有变化。  
        解决：实现类与interface只能在不同module才能生效。  
        
        