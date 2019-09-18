package com.xianguo.hotserver.socket.Handle;

/**
 * 处理器接口
 * @author Xianguo
 * @param <R> 处理器返回类型
 * @param <P> 处理器参数类型
 */
public interface BaseHandle<P,R> {
	
	/**
	 * 带参处理函数
	 * @author Xianguo
	 * @param parameter 处理器参数
	 * @return 返回处理器处理完成的值
	 */
	default R handle(P parameter) {
		return null;
	}
	
	/**
	 * 无参处理函数
	 * @author Xianguo
	 * @param parameter 处理器参数
	 * @return 返回处理器处理完成的值
	 */
	default R handle() {
		return null;
	}
}
