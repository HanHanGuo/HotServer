package com.xianguo.hotserver;

import com.xianguo.hotserver.config.LoadServerConfig;
import com.xianguo.hotserver.servlet.ServletContainer;
import com.xianguo.hotserver.socket.Core;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Server目录:"+System.getProperty("user.dir"));
		LoadServerConfig loadServerConfig = new LoadServerConfig();
		loadServerConfig.LoadServerConfigs();
		ServletContainer.loadServlet();
		Core core = new Core();
		core.connectionProcessingEngine();
	}
}
