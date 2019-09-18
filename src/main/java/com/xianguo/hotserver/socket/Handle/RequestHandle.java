package com.xianguo.hotserver.socket.Handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xianguo.hotserver.bean.RequestBaseInfo;
import com.xianguo.hotserver.bean.RequestHandleParameter;
import com.xianguo.hotserver.bean.RequestInfo;
import com.xianguo.hotserver.bean.enums.HttpMethod;
import com.xianguo.hotserver.bean.enums.HttpType;
import com.xianguo.hotserver.constants.HttpConstant;

/**
 * Http信息处理器
 * @author Xianguo
 */
public class RequestHandle implements BaseHandle<RequestHandleParameter, RequestInfo> {
	
	@Override
	public RequestInfo handle(RequestHandleParameter parameter) {
		//处理请求行
		String httpRequestInfo = parameter.getHead().toString();
		String[] info = httpRequestInfo.split("\r\n");
		String[] requestLin = info[0].split(" ");
		RequestBaseInfo requestBaseInfo = new RequestBaseInfo();
		
		requestBaseInfo.setRequestMethod(HttpMethod.valueOf(requestLin[0]));
		requestBaseInfo.setUrl(requestLin[1]);
		
		String[] httpTypeInfos = requestLin[2].split("/");
		
		requestBaseInfo.setHttpType(HttpType.valueOf(httpTypeInfos[0]));
		requestBaseInfo.setHttpVersion(httpTypeInfos[1]);
		
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setRequestBaseInfo(requestBaseInfo);
		//处理请求头
		Map<String, String> headMap= new HashMap<>();
		for(int i = 1;i<info.length;i++) {
			String[] headItems = info[i].split(": ");
			headMap.put(headItems[0], headItems[1]);
		}
		requestInfo.setRequestHead(headMap);
		//处理url参数
		String url = requestInfo.getRequestBaseInfo().getUrl();
		int parameterIndex = -1;
		if((parameterIndex = url.indexOf(HttpConstant.URL_SPLIT_SYMBOL)) != -1) {
			Map<String, List<String>> parameters = new HashMap<>();
			String parameterStr = url.substring(parameterIndex + 1);
			String[] urlParameters = parameterStr.split(HttpConstant.PARAMETER_SPLIT_SYMBOL);
			for(String urlParameter : urlParameters) {
				String[] keyAndValue = urlParameter.split(HttpConstant.EQUAL_SYMBOL);
				List<String> values = parameters.get(keyAndValue[0]);
				if(values == null) {
					values = new ArrayList<>();
				}
				values.add(keyAndValue.length > 1 ? keyAndValue[1] : null);
			}
			requestInfo.setParameters(parameters);
		}
		//处理FormData
		
		requestInfo.setContentIndex(parameter.getContentIndex());
		
		return requestInfo;
		
	}
	
}
