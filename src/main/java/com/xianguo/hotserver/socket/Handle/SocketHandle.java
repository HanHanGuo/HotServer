package com.xianguo.hotserver.socket.Handle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.xianguo.hotserver.bean.RequestHandleParameter;
import com.xianguo.hotserver.bean.RequestInfo;
import com.xianguo.hotserver.bean.ResponseInfo;
import com.xianguo.hotserver.constants.ServerConstant;

/**
 * 链接处理器将把请求封装为对象，然后交由其他处理器
 * 
 * @author Xianguo
 */
public class SocketHandle extends Thread {

	private Socket socket;

	public SocketHandle(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run(){
		try {
			// 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
			InputStream inputStream = socket.getInputStream();
			
			//处理基础请求信息BEGIN
			byte[] bytes = new byte[1024];
			StringBuilder requestInfoSb = new StringBuilder();
			int bytesIndex = -1;
			byte temp;
			String tempNewLine="";
			int headIndex = 0;
			while ((temp = (byte) inputStream.read()) != -1) {
				//下标加一
				headIndex++;
				//如果缓冲区满了，转为string，并且清空缓冲区
				if(bytesIndex == bytes.length-1) {
					bytesIndex = -1;
					requestInfoSb.append(new String(bytes, 0, bytes.length-1, ServerConstant.ENCODE));
					bytes = new byte[1024];
				}
				
				//存入数据到缓冲区
				bytesIndex++;
				bytes[bytesIndex] = temp;
				
				//判断是否读取基础信息到结尾
				if(temp == (byte)'\r' || temp == (byte)'\n') {
					tempNewLine += temp;
				}else {
					tempNewLine="";
				}
				if(tempNewLine.equals("13101310")) {
					requestInfoSb.append(new String(bytes, 0, bytesIndex-1, ServerConstant.ENCODE));
					break;
				}
			}
			//交给基础信息处理器把string处理为对象
			RequestHandle httpInfoHandle = new RequestHandle();
			RequestHandleParameter handleParameter = new RequestHandleParameter();
			handleParameter.setContentIndex(headIndex);
			handleParameter.setHead(requestInfoSb);
			handleParameter.setInputStream(inputStream);
			RequestInfo requestInfo = httpInfoHandle.handle(handleParameter);
			requestInfo.setIpAddress(socket.getInetAddress().getHostAddress());
			//处理基础请求信息END
			System.out.println(requestInfoSb);
			//处理request拿到response
			ResponseHandle requestHandle = new ResponseHandle();
			ResponseInfo responseInfo = requestHandle.handle(requestInfo);
			//写入返回头信息到stream
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write((responseInfo.getResponseString()).getBytes());
			//把返回体写入content部分
			FileInputStream fileInputStream = responseInfo.getContent();
			if(fileInputStream != null) {
				bytes = new byte[1024];
				int len = 0;
				while((len = fileInputStream.read(bytes)) != -1 ) {
					outputStream.write(bytes, 0, len);
				}
			}
			
			outputStream.close();
			fileInputStream.close();
			inputStream.close();
			socket.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
