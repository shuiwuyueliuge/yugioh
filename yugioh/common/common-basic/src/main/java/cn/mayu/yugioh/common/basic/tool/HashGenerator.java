package cn.mayu.yugioh.common.basic.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    public static String createHashStr(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(content.getBytes());
            return byteArray2HexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("create " + content + " Hash error " + e.getMessage());
        }
    }

    private static String byteArray2HexString(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < array.length; i++) {
            String hex = Integer.toHexString(array[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }

            sb.append(hex);
        }

        return sb.toString();
    }
}
