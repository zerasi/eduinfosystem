package com.web.sys.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class AES {
	public static String encrypt(String data, String key,String ivString) {
//		String ivString = "tdqsgf8888!@#$%^";
		//偏移量
		byte[] iv = ivString.getBytes();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = data.getBytes();
			int length = dataBytes.length;
			//计算需填充长度
			if (length % blockSize != 0) {
				length = length + (blockSize - (length % blockSize));
			}
			byte[] plaintext = new byte[length];
			//填充
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			//设置偏移量参数
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] encryped = cipher.doFinal(plaintext);
			return Base64.getEncoder().encodeToString(encryped);

//			return parseByte2HexStr(encryped);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String desEncrypt(String data, String key,String ivString) {
//		String ivString = "tdqsgf8888!@#$%^";
		byte[] iv = ivString.getBytes();
		try {
			byte[] encryp = Base64.getDecoder().decode(data);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
			byte[] original = cipher.doFinal(encryp);
			return new String(original);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}



	public static void main(String[] args) {
		Map<String,String> map = new HashMap<>();
//		map.put("username","aqczy");
//		map.put("vecode","9989");
//		map.put("veid","9989");
//		map.put("password","HoqwQoHlBuwBB8oLE7UXR7h6N7rLmum8KtGdGSeX1yT2bORaRjEzpfEmk9tUDAOvqegfNBIVhqnnMSGf4MFhKmQxnD0+hcH7Mj1iz/mJvajYrccK/vGUYyXNPqnBwN8ox/XN9jH8JKzhU58YpQu98sTqXJ/bKHc6aMuOUnjYcNg=");

		String data = "1400146931";
//		data = JSONObject.valueToString(map);
		String key = "tdqsgf8888!@#$%^";
		String iv = key;
//		String encrypt = encrypt(data, key,iv);
//		System.out.println("bef:" + data);
//		System.out.println("aft:" + encrypt);
//
//		String desEncrypt = desEncrypt(encrypt, key,iv);
//		System.out.println("aftdes:" + desEncrypt);
		T.print("第一次请求",desEncrypt("xn2ZmhDVyMjUzLNG1cY/kD5badyyjENGlyrbNfDWuRfScT4mft/Xe5Qe515E8kcdjOxrBtwI1sp6NxunZXXiKtwfpYPZaIFXbfGI6MekYrPFJ3pOaM+WmlR8gzO/0g4V+p0XY6OH6/nrDa/+5xyZtFDE9vtyvXShDXPgDvVsVS1Yh125hi9XuBqH1ow3Oej37CtEBx16iSFEK0xq0pSllVJIMJNRZjvKroU9xWjoqVi7u3kDn/JcQBgmC41DVG/xpleSRcQUtKmqe9C9VlJ794pfRiKtEBANP/tXdJ7z1X5E4fAo7vxWdiCr4XQWrBe8",key,iv));
		T.print("第一次响应：",desEncrypt("nysAfJN8VS3zp4/R6CMdRF3/QKPp0B+UO80BNU5zTpF+Ns8Hb5eZYi8UdDwcSGpr",key,iv));
		T.print("第二次请求：",desEncrypt("G8lEtKYKnI4zwqdcsdR8vMvWOr3+K48Lrq5Ud3NORoy1aeHtqfSl9cj+soZr/mTXO5L/togSSbVTn6vfGpCYrkgsyHcokuUsNaXr+X7rVDds8wtfh91vsdjPBxJhCAvO1MFA0YoJZ5cIXTCAh8S6ae4FVUPBRsfWWFQdX7JKGyNgBhReGq/2/FcZ7pk2sP0HO5k6p1qcfl+d7xvM4wfODSRa3kO9gd53pqLtoNtz3Q/OSeAxRsSQcsZpBc09PjpVYWH83pJLerafMhKEIkJQWsVbLx2SAFhDba3jMVVN8v0=",key,iv));



		T.print(desEncrypt("nysAfJN8VS3zp4/R6CMdRF3/QKPp0B+UO80BNU5zTpHNTs+mUmGpn9kpGYGkZwof",key,iv));

	}

}

