package com.autumn.common.util;

import com.autumn.common.constant.HashType;
import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.StringUtils;

public class HashUtil {

	private HashUtil() {

	}

	public static <T> String encrypt(T src, String salt, HashType hashType) {
		String srcStr = "";
		if (src instanceof String) {
			srcStr = (String) src;
		} else {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			srcStr = gson.toJson(src);
		}
		srcStr += srcStr + (StringUtils.isEmpty(salt) ? "" : salt);
		if (hashType == HashType.SHA256) {
			return DigestUtils.sha256Hex(srcStr);
		} else if (hashType == HashType.SHA512) {
			return DigestUtils.sha512Hex(srcStr);
		} else {
			return DigestUtils.md5Hex(srcStr);
		}
	}

	public static boolean hashVerify(String src, String salt, HashType hashType, String dist) {
		// 根据传入的密钥进行验证
		String shaText = encrypt(src, salt, hashType);
		if (shaText.equalsIgnoreCase(dist)) {
			return true;
		}
		return false;
	}
}
