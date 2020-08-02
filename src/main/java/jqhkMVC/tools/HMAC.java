package jqhkMVC.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jqhkMVC.Utils;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMAC {
	public static String hexFromBytes(byte[] array) {
		String hex = new BigInteger(1, array).toString(16);
		int zeroLength = array.length * 2 - hex.length();
		for (int i = 0; i < zeroLength; i++) {
			hex = "0" + hex;
		}
		return hex;
	}

	public static byte[] sign(String key, String message) {
		try {
			byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
			// 摘要算法用的是 HmacSHA256
			Mac sha256Hmac = Mac.getInstance("HmacSHA256");
			SecretKeySpec keySpec = new SecretKeySpec(byteKey, sha256Hmac.getAlgorithm());
			sha256Hmac.init(keySpec);
			byte[] result = sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));
			return result;
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Boolean verify(String key, String message, String signature) {
		// Utils.log("verify key %s message %s", key, message);
		byte[] sBytes = HMAC.sign(key, message);
		String rawSignature = hexFromBytes(sBytes);
		// Utils.log("verify old %s new %s", signature, rawSignature);
		return rawSignature.equals(signature);
	}

	public static void main(String[] args) {
	}
}
