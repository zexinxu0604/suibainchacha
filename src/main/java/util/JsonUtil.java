package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.Daily;
import model.GoldPrice;
import model.TuringMessage;

import javax.xml.soap.Text;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtil {
    public static JSONObject GetTuringRequest(TuringMessage turingMessage) {
        JSONObject jsonObject = new JSONObject();


        JSONObject inputText = new JSONObject();
        inputText.put("text", turingMessage.getPerception().getText());

        JSONObject inputImage = new JSONObject();
        inputImage.put("url", turingMessage.getPerception().getUrl());

        JSONObject perception = new JSONObject();
        perception.put("inputText", inputText);
        perception.put("inputImage", inputImage);

        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey", turingMessage.getUserInfo().getApiKey());
        userInfo.put("userId",  turingMessage.getUserInfo().getUserId());

        jsonObject.put("reqType", turingMessage.getReqType());
        jsonObject.put("perception", perception);
        jsonObject.put("userInfo", userInfo);

        return jsonObject;
    }

    public static String GetTuringResult(String result) {
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray results = jsonObject.getJSONArray("results");
        String userResult = "";
        System.out.println(results);
        for(int i = 0; i < results.size(); i++) {

            if(results.getJSONObject(i).get("resultType").toString().equals("url")){
                userResult = userResult + results.getJSONObject(i).getJSONObject("values").get("url").toString() + "\n";
            }
            else if (results.getJSONObject(i).get("resultType").toString().equals("text")) {
                userResult = userResult + results.getJSONObject(i).getJSONObject("values").get("text").toString() + "\n";
            }
        }
        System.out.println(userResult);
        return userResult;
    }

//    public static String

    public static GoldPrice getGoldPrice(JSONObject jsonObject) {
        GoldPrice goldPrice = new GoldPrice();

        goldPrice.setCode(jsonObject.get("code").toString());
        goldPrice.setServer(jsonObject.get("server").toString());
        goldPrice.setDd373(Float.parseFloat(jsonObject.get("dd373").toString()));
        goldPrice.setS5173(Float.parseFloat(jsonObject.get("s5173").toString()));
        goldPrice.setS7881(Float.parseFloat(jsonObject.get("s7881").toString()));
        goldPrice.setUu898(Float.parseFloat(jsonObject.get("uu898").toString()));
        goldPrice.setWanbaolou(Float.parseFloat(jsonObject.get("wanbaolou").toString()));
        goldPrice.setYoumu(Float.parseFloat(jsonObject.get("youmu_new").toString()));

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        goldPrice.setTime(simpleDateFormat.format(date));

        return goldPrice;
        
    }

    public static Daily getDaily(JSONObject jsonObject) {
        Daily daily = new Daily();

        daily.setTime(jsonObject.get("时间").toString());
        daily.setWeekday(jsonObject.get("星期").toString());
        daily.setDailySecretPlace(jsonObject.get("秘境大战").toString());
        daily.setWarPlace(jsonObject.get("今日战场").toString());
        daily.setPublicTask(jsonObject.get("驰援任务").toString());
        daily.setWulinPublicTask(jsonObject.get("武林通鉴·公共任务").toString());
        daily.setWulinSercretPlace(jsonObject.get("武林通鉴·秘境任务").toString());
        daily.setWulinGroupSecretPlace(jsonObject.get("武林通鉴·团队秘境").toString());

        if(jsonObject.get("美人画像") == null) {
            daily.setBeauty("无");
        } else {
            daily.setBeauty(jsonObject.get("美人画像").toString());
        }

        return daily;
    }

}
