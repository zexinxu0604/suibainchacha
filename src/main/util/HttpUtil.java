import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpUtil {
    private static String turingUrl = "http://openapi.tuling123.com/openapi/api/v2";

    public static String getTuringUrl() {
        return turingUrl;
    }


    //向图灵机器人发送聊天语句
    public static String SendTuringTextMessage(TextMessage textMessage) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(HttpUtil.getTuringUrl());
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization", "Basic YWRtaW46");
        String result;
        try{
            TuringMessage turingMessage = new TuringMessage();
            TextMessage resultMessage = new TextMessage();
            if(textMessage.getReceiveContent()!= null && textMessage.getFromUserName()!=null){

                turingMessage.setReqType(0);
                PerceptionBean preceptionBean = new PerceptionBean();
                preceptionBean.setText(textMessage.getReceiveContent());
                turingMessage.setPerception(preceptionBean);

                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUserId("112");
                turingMessage.setUserInfo(userInfoBean);

                //生成图灵机器人请求json
                JSONObject json = JsonUtil.GetTuringRequest(turingMessage);

                StringEntity s = new StringEntity(json.toString(), "utf-8");
                s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(s);
                // 发送请求
                HttpResponse httpResponse = client.execute(httpPost);
                InputStream inStream = httpResponse.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                result = strber.toString();

                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println("请求图灵服务器成功，做相应处理");
                } else {
                    System.out.println("请求服务端失败");
                }

                return result;
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //获取微信公众平台
    public static String GetAccessToken () {
        String access_token;
        String appsecret = "dd28c01d8458e40082b7ce850bccb01c";
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0bbb8a26577e107f&secret=" + appsecret);
                client = HttpClients.createDefault();
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                access_token = JSON.parseObject(result).get("access_token").toString();
                return access_token;
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取服务器信息
    public static int GetServerStatus(String server) {
        String status;
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                HttpGet httpGet = new HttpGet("https://jx3api.com/api/server.php?server=" + server);
                client = HttpClients.createDefault();
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                status = JSON.parseObject(result).get("code").toString();
                if(status.equals("1")) {
                    return Integer.parseInt(JSON.parseObject(result).get("status").toString());
                } else {
                    return 2;
                }

            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 2;
    }

    //日常查询
    public static String GetDaily(String server) {
        String status;
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                HttpGet httpGet = new HttpGet("https://jx3api.com/api/daily.php?server=" + server);
                client = HttpClients.createDefault();
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                return result;
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
