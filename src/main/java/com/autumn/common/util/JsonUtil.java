package com.autumn.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonUtil {

	private JsonUtil() {
	}

	/**
	 * 将object转化为json字符串
	 *
	 * @param object
	 * @param showNullField 为true时返回所有字段,为false时不返回为null的字段
	 * @param <T>
	 * @return 返回json字符串
	 */
	public static <T> String toJsonString(T object, boolean showNullField) {
		GsonBuilder builder = new GsonBuilder();
		if (showNullField) {
			builder.serializeNulls();
		}
		Gson gson = builder.create();
		return gson.toJson(object);
	}

	public static JsonObject toJsonObject(String jsonString, boolean showNullField) {
		GsonBuilder builder = new GsonBuilder();
		if (showNullField) {
			builder.serializeNulls();
		}
		Gson gson = builder.create();
		return gson.fromJson(jsonString, JsonObject.class);
	}

	public static JsonArray toJsonArray(String jsonString, boolean showNullField) {
		GsonBuilder builder = new GsonBuilder();
		if (showNullField) {
			builder.serializeNulls();
		}
		Gson gson = builder.create();
		return gson.fromJson(jsonString, JsonArray.class);
	}

	public static <T> T parseStr(String jsonString, Class<T> clz) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.fromJson(jsonString, clz);
	}
}
