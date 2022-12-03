package com.whk.rpc.registry;

import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class RegistryHandler {

	//用保存所有可用的服务
    private static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<>();

    //临时保存所有相关的服务类
    private final List<String> classNames = new ArrayList<>();
    
    public RegistryHandler(){
    	//完成递归扫描
    	scannerClass("com.whk.rpc.api.provider");
    	doRegister();
    }
    
    public MessageResponse invokeMethod(Object msg) throws Exception {
    	MessageResponse result = new MessageResponse();
		MessageRequest request = (MessageRequest)msg;
		System.out.println(request.getMessageId());
        //当客户端建立连接时，需要从自定义协议中获取信息，拿到具体的服务和实参
		//使用反射调用
        if(registryMap.containsKey(request.getClassName())){
        	Object clazz = registryMap.get(request.getClassName());
        	Method method = clazz.getClass().getMethod(request.getMethodName(), request.getTypeParameters());
        	result.setMessageId(request.getMessageId());
        	result.setError("");
        	result.setResult(new Object[]{method.invoke(clazz, request.getParametersVal())});
        }
		return result;
    }

	/**
     * 递归扫描类文件
     */
	private void scannerClass(String packageName){
		URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
		assert url != null;
		AtomicReference<File> dir = new AtomicReference<>(new File(url.getFile()));
		for (File file : Objects.requireNonNull(dir.get().listFiles())) {
			//如果是一个文件夹，继续递归
			if(file.isDirectory()){
				scannerClass(packageName + "." + file.getName());
			}else{
				classNames.add(packageName + "." + file.getName().replace(".class", "").trim());
			}
		}
	}

	/**
	 * 完成注册
	 */
	private void doRegister(){
		if(classNames.size() == 0){ return; }
		for (String className : classNames) {
			try {
				Class<?> clazz = Class.forName(className);
				Class<?> i = clazz.getInterfaces()[0];
				registryMap.put(i.getName(), clazz.getConstructors()[0].newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		classNames.clear();
	}
  
}
