package com.xianguo.hotserver.config;

import com.xianguo.hotserver.constants.ConfigConstant;
import com.xianguo.hotserver.constants.ServerConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Map;

/**
 * 加载服务器配置
 * @author Xianguo
 */
@Slf4j
public class LoadServerConfig {
	
	public void LoadServerConfigs() {
		LoadConfig loadConfig = new LoadConfig();
		Map<String, String> serverConfigMap = loadConfig.LoadConfigFile(ConfigConstant.SERVER_CONFIG_PATH);
		ServerConstant.PATH = System.getProperty("user.dir");
		ServerConstant.WEB_CONTENT = serverConfigMap.get("WEB_CONTENT");
		ServerConstant.SERVER_PORT = Integer.valueOf(serverConfigMap.get("SERVER_PORT"));
		ServerConstant.DEFAULT_INDEX = serverConfigMap.get("DEFAULT_INDEX");
		ServerConstant.MAX_CONNECTION_NUMBER = Integer.valueOf(serverConfigMap.get("MAX_CONNECTION_NUMBER"));
		ServerConstant.ENCODE = serverConfigMap.get("ENCODE");
		ServerConstant.WEB_CONTENT_PATH = ServerConstant.PATH + File.separator + ServerConstant.WEB_CONTENT;
		log.info("Server端口:"+ServerConstant.SERVER_PORT);
		log.info("Server内部编码:"+ServerConstant.ENCODE);
		log.info("Server地址"+ServerConstant.PATH);
		log.info("WebContent地址"+ServerConstant.WEB_CONTENT_PATH);
		log.info("默认主页地址:"+ServerConstant.DEFAULT_INDEX);
	}
	
}
