package com.xianguo.hotserver;

import com.xianguo.hotserver.config.LoadServerConfig;
import com.xianguo.hotserver.servlet.ServletContainer;
import com.xianguo.hotserver.servlet.WebAppLoader;
import com.xianguo.hotserver.socket.Core;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
public class App {
	public static void main(String[] args) throws Exception {
		log.info("Server目录:"+System.getProperty("user.dir"));
		LoadServerConfig loadServerConfig = new LoadServerConfig();
		loadServerConfig.LoadServerConfigs();
//		ServletContainer.loadServlet();
//		WebAppLoader.loadWebApp("DemoWebApp");
		WebAppLoader.loadWebApp("springboot-login");
//		WebAppLoader.loadWebApp("DemoWebApp2");
		Core core = new Core();
		core.connectionProcessingEngine();
	}
}
