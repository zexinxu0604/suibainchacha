package util;

import java.security.MessageDigest;
import java.util.Arrays;

public class CheckUtil {
    public static final String token = "shihuimin1113"; //开发者自行定义Tooken
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        if(token!= null && timestamp!=null && nonce!=null) {
            //1.定义数组存放tooken，timestamp,nonce
            String[] arr = {token,timestamp,nonce};
            //2.对数组进行排序
            Arrays.sort(arr);
            //3.生成字符串
            StringBuffer access_token = new StringBuffer();
            for(String s : arr){
                access_token.append(s);
            }
            //4.sha1加密,网上均有现成代码
            String temp = getSha1(access_token.toString());
            //5.将加密后的字符串，与微信传来的加密签名比较，返回结果
            return temp.equals(signature);
        } else {
            return false;
        }
    }

    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
}
