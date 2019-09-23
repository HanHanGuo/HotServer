package com.xianguo.hotserver.socket.Handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;

import com.xianguo.hotserver.bean.RequestInfo;
import com.xianguo.hotserver.bean.ResponseBaseInfo;
import com.xianguo.hotserver.bean.ResponseInfo;
import com.xianguo.hotserver.bean.enums.FileType;
import com.xianguo.hotserver.bean.enums.HttpStatusCode;
import com.xianguo.hotserver.constants.HttpConstant;
import com.xianguo.hotserver.constants.HttpConstant.HttpHeadName;
import com.xianguo.hotserver.servlet.ServletContainer;
import com.xianguo.hotserver.servlet.bean.HotServletRequest;
import com.xianguo.hotserver.servlet.bean.HotServletResponse;
import com.xianguo.hotserver.constants.ServerConstant;

/**
 * 请求处理
 * 
 * @author Xianguo
 */
public class ResponseHandle implements BaseHandle<RequestInfo, ResponseInfo> {

	@Override
	public ResponseInfo handle(RequestInfo parameter) {
		ResponseInfo responseInfo = new ResponseInfo();
		//构造时间
		String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		//创建response并写入时间返回头
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put(HttpHeadName.DATE, sdf.format(new Date()));
		//给返回头放入服务器信息
		responseMap.put(HttpHeadName.SERVER, ServerConstant.SERVER);
		responseMap.put(HttpHeadName.DEVELOPER, HttpConstant.DEVELOPER);

		ResponseBaseInfo responseBaseInfo = new ResponseBaseInfo();
		responseBaseInfo.setHttpType(parameter.getRequestBaseInfo().getHttpType());
		responseBaseInfo.setVersion(parameter.getRequestBaseInfo().getHttpVersion());

		
  		String url = parameter.getRequestBaseInfo().getUrl();
        /*Servlet servlet = ServletContainer.getServlet(url);
        if(servlet != null) {
        	try {
				((HttpServlet)servlet).service((HotServletRequest)parameter, (HotServletResponse)responseInfo);
			} catch (ServletException e) {
				responseBaseInfo.setStatus(HttpStatusCode.CODE_500);
			} catch (IOException e) {
				responseBaseInfo.setStatus(HttpStatusCode.CODE_404);
			}
        }*/
        
        //读取文件获得输入流
        //拼接访问文件地址并剃除问好
  		String filePath = ServerConstant.PATH;
  		if (!"/".equals(url)) {
  			filePath += url;
  		} else {
  			filePath += "\\" + ServerConstant.DEFAULT_INDEX;
  		}
  		int suffixIndex = 0;
  		if((suffixIndex = filePath.indexOf("?")) != -1){
  			filePath = filePath.substring(0,suffixIndex);
  		}
		File file = new File(filePath);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			responseBaseInfo.setStatus(HttpStatusCode.CODE_404);
		}
		if(fileInputStream != null) {
			responseInfo.setContent(fileInputStream);
			
			responseMap.put(HttpHeadName.CONTENT_LENGTH, file.length()+"");
			String fileName = file.getName();
	        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
	        FileType contentType = FileType.ValueOf(suffix.toLowerCase());
	        if(contentType == null) {
	        	contentType = FileType.TYPE_ALL;
	        }
			responseMap.put(HttpHeadName.CONTENT_TYPE, contentType.getContentType());
			
			responseBaseInfo.setStatus(HttpStatusCode.CODE_200);
		}
		
		responseInfo.setResponseBaseInfo(responseBaseInfo);
		responseInfo.setResponseHead(responseMap);
		return responseInfo;
	}

}
