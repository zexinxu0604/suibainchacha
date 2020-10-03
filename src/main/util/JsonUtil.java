import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.xml.soap.Text;

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

}
