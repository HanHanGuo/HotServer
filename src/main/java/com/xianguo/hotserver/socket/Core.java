package com.xianguo.hotserver.socket;

import com.xianguo.hotserver.constants.ServerConstant;
import com.xianguo.hotserver.exception.ServerPortOccupiedException;
import com.xianguo.hotserver.socket.Handle.SocketHandle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * <链接处理引擎> <链接接入点，从这里拿到链接并进行处理>
 * 
 * @author Xianguo
 * @version [版本号,2019/07/30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Slf4j
public class Core {

	public void connectionProcessingEngine() throws Exception {

		// 构建链接线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(ServerConstant.MAX_CONNECTION_NUMBER);
		
		ServerSocket server = null;
		// 构造连接器
		try {
			server = new ServerSocket(ServerConstant.SERVER_PORT);
		} catch (IOException e) {
			throw new ServerPortOccupiedException();
		}

		// server将一直等待连接的到来
		log.info("server启动成功");

		while (true) {
			//取到链接
			Socket socket = server.accept();
			//构建处理器
			SocketHandle socketHandle = new SocketHandle(socket);
			//提交到线程池
			threadPool.submit(socketHandle);
		}
		
	}

}
