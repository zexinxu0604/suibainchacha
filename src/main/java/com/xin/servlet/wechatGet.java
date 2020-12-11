package com.xin.servlet;

import com.xin.model.Daily;
import com.xin.model.GoldPrice;
import com.xin.model.ImageMessage;
import com.xin.model.TextMessage;
import org.dom4j.DocumentException;
import com.xin.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class wechatGet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            //如果校验成功，将得到的随机字符串原路返回
            out.print(echostr);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //将request请求，传到Message工具类的转换方法中，返回接收到的Map对象
            Map<String, String> map = MessageUtil.xmlToMap(request);
            //从集合中，获取XML各个节点的内容
            switch (map.get("MsgType")) {
                case "text" : {
                    TextMessage message = MessageUtil.mapToTextMessage(map);
                    String content = message.getReceiveContent();

                    switch (TextUtil.TextKind(content)) {
                        case 0: {
                            String result = HttpUtil.SendTuringTextMessage(message);

                            message.setContent(JsonUtil.GetTuringResult(result));
                            //调用Message工具类，将对象转为XML字符串
                            String str = MessageUtil.textMessageToXml(message);
                            out.print(str);
                            break;
                        }
                        //查询开服情况
                        case 1: {
                            String server = content.replaceFirst("开服","").trim();
                            int result = HttpUtil.GetServerStatus(server);
                            message.setContent(TextUtil.GetServerResult(result, server));
                            String str = MessageUtil.textMessageToXml(message);
                            out.print(str);
                            break;
                        }
                        case 2: {
                            String server = content.replaceFirst("日常","").trim();
                            server = server.replaceFirst("查询","").replaceAll(" ", "");
                            Daily daily = TextUtil.getDaily(server);
                            String str;

                            if(daily != null) {
                                String result = "【时间】 " + daily.getTime() + " 星期" + daily.getWeekday() + "\n" + "【今日大战】 " + daily.getDailySecretPlace() + "\n";
                                result += "【今日战场】 " + daily.getWarPlace() + "\n" + "【驰援任务】 " + daily.getPublicTask() + "\n" + "【美人图】 " + daily.getBeauty() + "\n";
                                result += "【武林通鉴·公共任务】 " + daily.getWulinPublicTask() + "\n" + "【武林通鉴·秘境任务】 " + daily.getWulinSercretPlace() + "\n" + "【武林通鉴·团队秘境】 " + daily.getWulinGroupSecretPlace();

                                message.setContent(result);
                                str = MessageUtil.textMessageToXml(message);
                            }
                            else {
                                String result = "没有查到相应信息";
                                message.setContent(result);
                                str = MessageUtil.textMessageToXml(message);
                            }
                            out.print(str);
                            break;
                        }
                        case 3: {
                            String server = content.replaceFirst("金价","").trim();
                            GoldPrice goldPrice = HttpUtil.getGoldPrice(server);
                            String result;
                            if(goldPrice == null) {
                                result = "输入的服务器有问题qwq，重新查一下吧";
                            } else {
                                result = "【金价仅供参考，实际情况以交易时为准】";
                                result += "\n" + "【服务器】 "+ goldPrice.getServer() + "\n" + "【万宝楼】 " + Float.toString(goldPrice.getWanbaolou()) + "\n" + "【uu898平台】" + Float.toString(goldPrice.getUu898()) + "\n" + "【dd373平台】 " + Float.toString(goldPrice.getDd373()) + "\n";
                                result += "【5173平台】 " + Float.toString(goldPrice.getS5173()) + "\n" + "【7881平台】 " + Float.toString(goldPrice.getS7881()) + "\n" + "【游募平台】 " + Float.toString(goldPrice.getYoumu());
                            }

                            message.setContent(result);
                            String str = MessageUtil.textMessageToXml(message);
                            out.print(str);

                            break;
                        }
                    }
                }

                case "image" : {
                    ImageMessage message = MessageUtil.mapToImageMessage(map);
                    message.setImage(new ImageMessage.ImageBean(message.getReceiveMediaId()));
                    String str = MessageUtil.imageMessageToXml(message);
                    System.out.println(str);
                    out.print(str);
                    break;
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}


