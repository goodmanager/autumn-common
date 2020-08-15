package com.autumn.common.constant;

public enum JwtClaimsKey {

	X_AppId("X-AppId"),
	X_Uid("X-Uid"),
	X_ACCESSTOKEN("X-ACCESSTOKEN");

	String key;

	JwtClaimsKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
