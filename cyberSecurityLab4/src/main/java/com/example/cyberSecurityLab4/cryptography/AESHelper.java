package com.example.cyberSecurityLab4.cryptography;

import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;

/**
 * AES 128bit 加密解密工具类
 */
public class AESHelper {
    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！

    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);	// 加密

            return Base64.getEncoder().encodeToString(encrypted); 	//通过Base64转码返回

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = Base64.getDecoder().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec); //使用密钥初始化，设置为解密模式

            byte[] original = cipher.doFinal(encrypted1);	//执行操作
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *   用于生成一组16位随机数 key
     * @return
     */
    public static String getRandomStringKey() {
        int hashCodeValue = UUID.randomUUID().hashCode();
        if(hashCodeValue < 0) hashCodeValue = -hashCodeValue;
        return String.format("%016d",hashCodeValue);//左边补0,16位，进制（d,x）
    }

    /**
     *   用于生成16位的随机数 iv
     * @return
     */
    public static String getRandomStringIv(){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random=new Random();
        StringBuffer key = new StringBuffer();
        for(int i = 0; i < 16; i ++){
            int keyNumber = random.nextInt(base.length());
            key.append(base.charAt(keyNumber));
        }
        return key.toString();
    }


    /**
     * 测试
     */
    public static void main(String args[]) throws Exception {
        String data = "admin123";
        String key = getRandomStringKey();
        String iv = getRandomStringIv();

        String enData = encrypt(data, key, iv);

        System.out.println(key);
        System.out.println(iv);
        System.out.println(enData);
        System.out.println(desEncrypt(enData, key, iv));
    }
}
