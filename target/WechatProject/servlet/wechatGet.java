import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");
            String MsgId = map.get("MsgId");
            if (MsgType.equals("text")) {//判断消息类型是否是文本消息(text)
                TextMessage message = new TextMessage();
                //原来【接收消息用户】变为回复时【发送消息用户】
                message.setFromUserName(ToUserName);
                message.setToUserName(FromUserName);
                message.setMsgType("text");
                message.setCreateTime(new Date().getTime());//创建当前时间为消息时间
                message.setContent("您好，" + FromUserName + "\n我是：" + ToUserName
                        + "\n您发送的消息类型为：" + MsgType + "\n您发送的时间为" + CreateTime
                        + "\n我回复的时间为：" + message.getCreateTime() + "\n您发送的内容是：" + Content);
                //调用Message工具类，将对象转为XML字符串
                String str = MessageUtil.textMessageToXml(message);
                System.out.println(str);
                out.print(str);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}


