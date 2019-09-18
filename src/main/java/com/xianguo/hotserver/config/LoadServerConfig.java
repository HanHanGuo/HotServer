package com.xianguo.hotserver.config;

import java.util.Map;

import com.xianguo.hotserver.constants.ConfigConstant;
import com.xianguo.hotserver.constants.ServerConstant;

/**
 * 加载服务器配置
 * @author Xianguo
 */
public class LoadServerConfig {
	
	public void LoadServerConfigs() {
		LoadConfig loadConfig = new LoadConfig();
		Map<String, String> serverConfigMap = loadConfig.LoadConfigFile(ConfigConstant.SERVER_CONFIG_PATH);
		ServerConstant.PATH = serverConfigMap.get("PATH");
		ServerConstant.SERVER_PORT = Integer.valueOf(serverConfigMap.get("SERVER_PORT"));
		ServerConstant.DEFAULT_INDEX = serverConfigMap.get("DEFAULT_INDEX");
		ServerConstant.MAX_CONNECTION_NUMBER = Integer.valueOf(serverConfigMap.get("MAX_CONNECTION_NUMBER"));
		ServerConstant.ENCODE = serverConfigMap.get("ENCODE");
		System.out.println("Server端口:"+ServerConstant.SERVER_PORT);
		System.out.println("Server内部编码:"+ServerConstant.ENCODE);
		
		if(ServerConstant.PATH.indexOf(":") == -1) {
			if(ServerConstant.PATH.startsWith("/") || ServerConstant.PATH.startsWith("\\")) {
				ServerConstant.PATH = System.getProperty("user.dir") + ServerConstant.PATH;
			}else {
				ServerConstant.PATH = System.getProperty("user.dir") + "\\" + ServerConstant.PATH;
			}
		}
		System.out.println("Server工作空间:"+ServerConstant.PATH);
		System.out.println("默认主页地址:"+ServerConstant.DEFAULT_INDEX);
	}
	
}
