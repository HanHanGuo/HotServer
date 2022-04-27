package com.xianguo.hotserver.config;

import com.xianguo.hotserver.constants.ConfigConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载配置文件
 * @author Xianguo
 */
public class LoadConfig {
	
	/**
	 * 加载配置文件内容到服务器
	 * @author Xianguo
	 * @param path 文件路径
	 */
	public Map<String, String> LoadConfigFile(String path) {
		try {
			Map<String, String> values = new HashMap<>();
			File file = new File(path);
			FileInputStream fileInputStream = new FileInputStream(file);
			Boolean isNotesLine = false;
			Boolean isValue = false;
			int temp = -1;
			byte[] fieldName = new byte[1024];
			byte[] fieldValue = new byte[1024];
			int fieldNameIndex = 0;
			int fieldValueIndex = 0;
			while((temp = fileInputStream.read()) != -1) {
				if(temp == 35) {
					isNotesLine = true;
					continue;
				}
				if(isNotesLine && temp == 10) {
					isNotesLine = false;
					continue;
				}
				if(isNotesLine) {
					continue;
				}
				if(temp == 61) {
					isValue = true;
					continue;
				}
				//换行不处理
				if(temp == 10 || temp == 13) {
					continue;
				}
				//一行读取完毕
				if(temp == 59) {
					//开始注入数据
					values.put(new String(fieldName,0,fieldNameIndex,ConfigConstant.ENCODE), new String(fieldValue,0,fieldValueIndex,ConfigConstant.ENCODE));
					fieldNameIndex=0;
					fieldValueIndex=0;
					fieldName = new byte[1024];
					fieldValue = new byte[1024];
					isValue = false;
					continue;
				}
				//不是注释，开始读取数据
				//名称
				if(!isNotesLine && !isValue) {
					fieldName[fieldNameIndex] = (byte) temp;
					fieldNameIndex++;
				}
				//值
				if(!isNotesLine && isValue) {
					fieldValue[fieldValueIndex] = (byte) temp;
					fieldValueIndex++;
				}
			}
			fileInputStream.close();
			return values;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
