package jqhkMVC.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import jqhkMVC.Utils;
import org.apache.commons.codec.binary.Base64; //导入方法依赖的package包/类

public class JWTUtils {

	public static String JSONEncodeBase64(JSONObject object) {
		String json = JSON.toJSONString(object); // 得到字符串 "{"typ":"JWT"}"
		String r = Base64.encodeBase64URLSafeString(json.getBytes());
		return r;
	}

	public static String jwt(String key, JSONObject header, JSONObject payload) {
		String headerString = JSONEncodeBase64(header);
		String payloadString = JSONEncodeBase64(payload);
		String sBase64 = String.format("%s.%s", headerString, payloadString);
		// Utils.log("s %s\nsBase64 %s", s, sBase64);
		byte[] signatureBytes = HMAC.sign(key, sBase64);
		String signature = HMAC.hexFromBytes(signatureBytes);
		String signatureBase64 = Base64.encodeBase64URLSafeString(signature.getBytes(StandardCharsets.UTF_8));
		String jwtToken = String.format("%s.%s", sBase64, signatureBase64);
		return jwtToken;
	}

	public static JSONObject jwtDecode(String key, String jwt) {
		String[] jwtList = jwt.split("\\.");
		// 用于查看 加密方式
		String headerBase64 = jwtList[0];
		// 内容
		String payloadBase64 = jwtList[1];
		// 签名
		String signatureBase64 = jwtList[2];
		String s = String.format("%s.%s", headerBase64, payloadBase64);
		String signature = new String(Base64.decodeBase64(signatureBase64), StandardCharsets.UTF_8);
		if (HMAC.verify(key, s, signature)) {
			String headerString = new String(Base64.decodeBase64(headerBase64), StandardCharsets.UTF_8);
			String payloadString = new String(Base64.decodeBase64(payloadBase64), StandardCharsets.UTF_8);
			// JSONObject header = JSON.parseObject(headerString);
			JSONObject payload = JSON.parseObject(payloadString);
			// Utils.log("header %s\npayload %s", header, payload);
			// 这里 header 可以得到签名的加密手法
			return payload;
		}
		return null;
	}

	public static void testJwtDecode() {
		JSONObject header = new JSONObject(true);
		header.put("typ", "JWT");
		header.put("alg", "HS256");

		JSONObject payload = new JSONObject(true);
		payload.put("userId", 5);
		Long iat = System.currentTimeMillis() / 1000L;
		Long exp = iat + (24 * 60 * 60);
		payload.put("iat", iat);
		payload.put("exp", exp);

		String key = "my-secret";
		String stringEncoded = jwt(key, header, payload);
		Utils.log("testJwtDecode jwt %s", stringEncoded);
		JSONObject h = jwtDecode(key, stringEncoded);
		Utils.log("testJwtDecode header %s", h);
		Utils.ensure(h.get("userId").equals(5), "testDecode1");
		Utils.log("%s %s", h.get("iat"), iat);
		Utils.ensure(h.get("iat").equals(iat), "testDecode2");
	}

	public static void main(String[] args) {
		testJwtDecode();
	}
}


	//
	//
	// public static void testJSONEncodeBase64() {
	// 	JSONObject header = new JSONObject(true);
	// 	header.put("typ", "JWT");
	// 	header.put("alg", "HS256");
	//
	// 	JSONObject payload = new JSONObject(true);
	// 	payload.put("sub", "1234567890");
	// 	payload.put("name", "John Doe");
	// 	payload.put("iat", 1516239022);
	//
	// 	String headerString = JSONEncodeBase64(header);
	// 	String payloadString = JSONEncodeBase64(payload);
	// 	String r = String.format("%s.%s", headerString, payloadString);
	// 	Utils.log("header+payload %s", r);
	// 	String e = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ";
	// 	Utils.ensure(r.equals(e), "testJSONEncodeBase64");
	// }