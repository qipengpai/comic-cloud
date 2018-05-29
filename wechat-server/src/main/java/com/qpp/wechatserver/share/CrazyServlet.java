package com.qpp.wechatserver.share;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class CrazyServlet {
    //	public static String APPID = "wx0e0ea8f7955b1fcc";//appid 改为自己的appid
//	public static String APPSECRET = "3457cbc42a1f5fc35d211a63fa0ffc50";//红色
    public final static String APPID ="wx053e2bdaaf81ab7a";
    public final static String APPSECRET ="0b6f996c50e98740d89f29d395fe1aad";
    // 凭证获取（GET）
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        Map<String, String> params;
        try {
            params = Sign.sign(TickFile.getTicke(),url);
            System.out.println("sign----------  "+url);
            JSONObject jsonObject = JSONObject.fromObject(params);
            jsonObject.put("appid", APPID);
            String jsonStr = jsonObject.toString();
            System.out.println("jsonStr"+jsonStr);
            PrintWriter out = response.getWriter();
            out.write(jsonStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

}
