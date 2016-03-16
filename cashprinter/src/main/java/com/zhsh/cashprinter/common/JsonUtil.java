/**
 * 
 */
package com.zhsh.cashprinter.common;


import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;

/**
 * @author zhangsheng
 *
 */
public class JsonUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	static {
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}
	
	public static String convertObjToJson(Object obj) throws Exception {
		String retJson = "";
		try {
			objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
			retJson = objectMapper.writeValueAsString(obj);
		}  catch (Exception e) {
			throw new Exception("将对象转换成json出现异常：", e);
		}
		return retJson;
	}
	
	public static <T> T convertJsonToObj(String json, Class<T> clazz) throws Exception{
		T retObj = null;
		try {
			retObj = objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new Exception("将json转换成对象出现异常：", e);
		}
		return retObj;
	}
	
	public static <T> List<T> convertJsonToList(String json, Class<T> clazz) throws Exception{
		List<T> retList = null;
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
			retList = objectMapper.readValue(json, javaType);
		} catch (Exception e) {
			throw new Exception("将json转换成list出现异常：", e);
		}
		return retList;
	}
}
