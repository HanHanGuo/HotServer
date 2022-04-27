package com.xianguo.hotserver.socket.Handle;

import com.xianguo.hotserver.bean.RequestInfo;
import com.xianguo.hotserver.bean.ResponseBaseInfo;
import com.xianguo.hotserver.bean.ResponseInfo;
import com.xianguo.hotserver.bean.enums.FileType;
import com.xianguo.hotserver.bean.enums.HttpStatusCode;
import com.xianguo.hotserver.constants.HttpConstant;
import com.xianguo.hotserver.constants.HttpConstant.HttpHeadName;
import com.xianguo.hotserver.constants.ServerConstant;
import com.xianguo.hotserver.servlet.WebAppLoader;
import com.xianguo.hotserver.servlet.impl.HotServletRequest;
import com.xianguo.hotserver.servlet.impl.HotServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 请求处理
 * 
 * @author Xianguo
 */
@Slf4j
public class ResponseHandle implements BaseHandle<RequestInfo, ResponseInfo> {

	@Override
	public ResponseInfo handle(RequestInfo parameter) {
		ResponseInfo responseInfo = new HotServletResponse();
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
		HotServletRequest servletRequest = (HotServletRequest) parameter;
		HotServletResponse servletResponse = (HotServletResponse) responseInfo;

		//执行拦截器
		List<Filter> filterList = WebAppLoader.getFilter(url);
		for (Filter filter : filterList) {
			try {
				AtomicReference<Boolean> isContinue = new AtomicReference<>(false);
				filter.doFilter(servletRequest, servletResponse, (req,res)->{
					isContinue.set(true);
				});
				if(!isContinue.get()){
					break;
				}
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//执行servlet
		Servlet servlet = null;
		try {
		  servlet = WebAppLoader.getServlet(url);
		  if (servlet != null) {
			  try {
				  servletRequest.setContextPath(servlet.getServletConfig().getServletContext().getContextPath());
				  servlet.service(servletRequest, servletResponse);
				  responseBaseInfo.setStatus(HttpStatusCode.CODE_200);
				  responseMap.put(HttpHeadName.CONTENT_TYPE, FileType.HTML.getContentType());
			  } catch (ServletException e) {
				  e.printStackTrace();
			  } catch (IOException e) {
				  e.printStackTrace();
			  } catch (ClassCastException e) {
				  e.printStackTrace();
			  } catch (Exception e) {
				  log.error(e.getMessage(), e);
			  }
		  }
		} catch (ServletException e) {
			e.printStackTrace();
		}
        
        //读取文件获得输入流
        //拼接访问文件地址并剃除问好
		if(servlet == null) {
			String filePath = ServerConstant.WEB_CONTENT_PATH;
			if (!"/".equals(url)) {
				filePath += url;
			} else {
				filePath += "\\" + ServerConstant.DEFAULT_INDEX;
			}
			int suffixIndex = 0;
			if ((suffixIndex = filePath.indexOf("?")) != -1) {
				filePath = filePath.substring(0, suffixIndex);
			}
			File file = new File(filePath);
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {
				responseBaseInfo.setStatus(HttpStatusCode.CODE_404);
			}
			if (fileInputStream != null) {
				responseInfo.setContent(fileInputStream);

				responseMap.put(HttpHeadName.CONTENT_LENGTH, file.length() + "");
				String fileName = file.getName();
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				FileType contentType = FileType.ValueOf(suffix.toLowerCase());
				if (contentType == null) {
					contentType = FileType.TYPE_ALL;
				}
				responseMap.put(HttpHeadName.CONTENT_TYPE, contentType.getContentType());
				responseBaseInfo.setStatus(HttpStatusCode.CODE_200);

			}
		}
		
		responseInfo.setResponseBaseInfo(responseBaseInfo);
		responseInfo.setResponseHead(responseMap);
		return responseInfo;
	}

}
