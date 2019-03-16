/**   
* @Title: GsonTypeAdapter.java 
* @Package com.justnd.octoryeserver.utils 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月9日 下午10:19:08  
*/
package com.justnd.octoryeserver.util;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * @ClassName: GsonTypeAdapter
 * @Description: TODO 将Gson对象里值为Null的字段适配为空字符串
 * @author JD
 * @date 2019年1月9日 下午10:19:08
 * 
 */
public class NullValueTypeAdapter extends TypeAdapter<String> {
	
	@Override
	public String read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return "";
		}

		return reader.nextString();
	}

	@Override
	public void write(JsonWriter writer, String value) throws IOException {
		if (value == null) {
			writer.value("");
			return;
		}
		writer.value(value);
	}
}
