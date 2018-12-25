package com.qpp.wechatserver.swiftpass.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qpp.wechatserver.swiftpass.config.SwiftpassConfig;
import com.qpp.wechatserver.swiftpass.util.SignUtils;
import com.qpp.wechatserver.swiftpass.util.XmlUtils;
import org.apache.log4j.Logger;

/**
 * <一句话功能简述>
 * <功能详细描述>通知
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestPayResultSerlet extends HttpServlet {
    private static Logger log = Logger.getLogger(TestPayResultSerlet.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.debug("收到通知...");
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setHeader("Content-type", "text/html;charset=UTF-8");
            String resString = XmlUtils.parseRequst(req);
            log.debug("请求的内容：" + resString);
            
            String respString = "error";
            if(resString != null && !"".equals(resString)){
                Map<String,String> map = XmlUtils.toMap(resString.getBytes(), "utf-8");
                String res = XmlUtils.toXml(map);
                log.debug("请求结果：" + res);
                if(map.containsKey("sign")){
                    if(!SignUtils.checkParam(map, SwiftpassConfig.key)){
                        res = "验证签名不通过";
                        respString = "error";
                    }else{
                        String status = map.get("status");
                        if(status != null && "0".equals(status)){
                            String result_code = map.get("result_code");
                            if(result_code != null && "0".equals(result_code)){
                                respString = "success";
                            } 
                        } 
                    }
                }
            }
            resp.getWriter().write(respString);
        } catch (Exception e) {
           log.error("操作失败，原因：",e);
        }
    }
}
