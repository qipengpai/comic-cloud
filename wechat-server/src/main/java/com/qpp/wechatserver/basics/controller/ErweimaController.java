package com.qpp.wechatserver.basics.controller;

import com.qpp.wechatserver.basics.entity.UserEntity;
import com.qpp.wechatserver.dao.impl.WeiXinDaoImpl;
import com.qpp.wechatserver.model.OAuthAccessToken;
import com.qpp.wechatserver.model.useValue;
import com.qpp.wechatserver.share.Sign;
import com.qpp.wechatserver.share.TickFile;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ErweimaController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/")
    public String index(Model model) throws UnsupportedEncodingException {
        String oauthUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        String redirect_uri = URLEncoder.encode("47.104.94.237:8666/callback", "utf-8");
        oauthUrl =  oauthUrl.replace("APPID", useValue.AppId).replace("REDIRECT_URI",redirect_uri).replace("SCOPE","snsapi_userinfo");
        model.addAttribute("name","liuzp");
        model.addAttribute("oauthUrl",oauthUrl);
        return "index";
    }

    @ResponseBody
    @PostMapping("/callBack")
    public String callBack(String code,String state,Model model) throws Exception {
        WeiXinDaoImpl dao = new WeiXinDaoImpl();
        UserEntity entity = null;
        try {
            logger.info("进入授权回调,code:{},state:{}", code, state);
            OAuthAccessToken oac = dao.getOAuthAccessToken(useValue.AppId, useValue.AppSecret, code);// 通过code换取网页授权access_token
            entity = dao.acceptOAuthUserNews(oac.getAccessToken(), oac.getOpenid());// 获取用户信息
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("-------------------微信拉去用户数据失败--------------------------");
        }
        return entity.getUserid();
    }

    @ResponseBody
    @RequestMapping(value = "/share", method = {RequestMethod.POST, RequestMethod.GET })
    public String business(HttpServletRequest request) throws ServletException, IOException {

        String url = request.getParameter("url");
        Map<String, String> params;
        try {
            params = Sign.sign(TickFile.getTicke(), url);
            System.out.println("sign----------  " + url);
            JSONObject jsonObject = JSONObject.fromObject(params);
            jsonObject.put("appid", useValue.AppId);
            String jsonStr = jsonObject.toString();
            System.out.println("jsonStr" + jsonStr);
            return jsonStr;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
