package com.autumn.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesUtil {

	private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/GCM/PKCS5Padding"; // 默认的加密算法

	private AesUtil() {
	}

	/**
	 * AES 加密
	 *
	 * @param src        要加密的字符串
	 * @param encryptKey 密钥
	 * @return 加密后的字符串
	 */
	public static String encrypt(String src, String encryptKey) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(encryptKey));
			byte[] iv = cipher.getIV();
			assert iv.length == 12;
			byte[] encryptData = cipher.doFinal(src.getBytes());
			assert encryptData.length == src.getBytes().length + 16;
			byte[] message = new byte[12 + src.getBytes().length + 16];
			System.arraycopy(iv, 0, message, 0, 12);
			System.arraycopy(encryptData, 0, message, 12, encryptData.length);
			return new String(message, StandardCharsets.UTF_8);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
			| BadPaddingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * AES 解密
	 *
	 * @param src        要解密的字符串
	 * @param encryptKey 秘钥
	 * @return 返回解密后的字符串
	 */
	public static String decrypt(String src, String encryptKey) {
		byte[] content = src.getBytes(StandardCharsets.UTF_8);
		if (content.length < 12 + 16) throw new IllegalArgumentException();
		GCMParameterSpec params = new GCMParameterSpec(128, content, 0, 12);
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(encryptKey), params);
			byte[] decryptData = cipher.doFinal(content, 12, content.length - 12);
			return new String(decryptData);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 生成加密秘钥
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static SecretKeySpec getSecretKey(String encryptPass) throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
		kg.init(128, new SecureRandom(encryptPass.getBytes()));
		SecretKey secretKey = kg.generateKey();
		return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM); // 转换为AES专用密钥
	}
}
