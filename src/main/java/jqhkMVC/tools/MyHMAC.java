package jqhkMVC.tools;

import jqhkMVC.Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

public class MyHMAC {
	private static final int blockSize = 256;

	private static final byte I_PAD = 0x36;
	private static final byte O_PAD = 0x5C;

	public static String hmac(String key, String message) {
		byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
		byte[] messageByte = message.getBytes(StandardCharsets.UTF_8);
		int zeroLen = blockSize - byteKey.length;
		byteKey = fillZero(byteKey, zeroLen);
		byte[] o_pad = initBytes(O_PAD, blockSize);
		byte[] i_pad = initBytes(I_PAD, blockSize);
		byte[] o_key_pad = xorBytes(byteKey, o_pad);
		byte[] i_key_pad = xorBytes(byteKey, i_pad);

		byte[] r1 = mergeArray(i_key_pad, messageByte);
		String result = hash(mergeArray(o_key_pad, hash(r1).getBytes()));
		return result;
	}

	public static void main(String[] args) {
		String key = "test";
		String message = "hello, my friend1";
		String signature = hmac(key, message);
		Utils.log("test hmac %s \n\n %s", new String(signature), new String(hmac("test", "hello, my friend")));
	}

	private static byte[] fillZero(byte[] data, int zeroLen) {
		int len = data.length + zeroLen;
		byte[] result = new byte[len];
		// 自动初始化 全为0
		byte[] zeroBytes = new byte[zeroLen];
		result = mergeArray(data, zeroBytes);
		return result;
	}

	private static byte[] mergeArray(byte[] array1, byte[] array2) {
		int len = array1.length + array2.length;
		byte[] result = new byte[len];
		// 自动初始化 全为0
		System.arraycopy(array1, 0, result, 0, array1.length);
		System.arraycopy(array2, 0, result, array1.length, array2.length);
		return result;
	}

	private static byte[] initBytes(byte element, int len) {
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = element;
		}
		return result;
	}

	private static byte[] xorBytes(byte[] array1, byte[] array2) {
		byte[] r = new byte[array1.length];
		for (int i = 0; i < array1.length; i++) {
			byte e1 = array1[i];
			byte e2 = array2[i];
			r[i] = (byte)((e1 ^ e2) & 0x000000ff);
		}
		return r;
	}

	private static String hash(byte[] array) {
		String str = new String(array, StandardCharsets.UTF_8);
		str = md5(str);
		return str;
	}

	public static String hexFromBytes(byte[] array) {
		String hex = new BigInteger(1, array).toString(16);
		int zeroLength = array.length * 2 - hex.length();
		for (int i = 0; i < zeroLength; i++) {
			hex = "0" + hex;
		}
		return hex;
	}

	public static String md5 (String origin) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(origin.getBytes());
			byte[] result = md.digest();
			String hex = hexFromBytes(result);
			return hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("error: %s", e));
		}
	}
}
