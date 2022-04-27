package com.xianguo.hotserver.exception;

import com.xianguo.hotserver.constants.ExceptionMsgConstant;
import com.xianguo.hotserver.constants.ServerConstant;

/**
 * 端口被占用Exception
 * @author Xianguo
 */
public class ServerPortOccupiedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return ServerConstant.SERVER_PORT+ExceptionMsgConstant.SERVER_PORTOCCUPIED_EXCEPTION;
	}
	
}
