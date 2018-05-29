package com.qpp.wechatserver.share;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

/**
 * 通用工具类
 * 
 * @author liufeng
 * @date 2013-10-17
 */
public class CommonUtil {
	
	public static Log log = LogFactory.getLog(CommonUtil.class);
	// 凭证获取（GET）
	public  static	 String titck_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			System.out.println("正在发送请求");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			//System.out.println("接受到请求了！！");
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			//System.out.println("-----------");
			//System.out.println(buffer.toString());
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}

	public static void main(String[] args) {
		CommonUtil commonUtil = new CommonUtil();
		JSONObject tokenString = CommonUtil.httpsRequest(titck_url, "GET", null);
		JSONObject.fromObject(tokenString);
		//		sql.substring("openid:",",'scope'");
		String access_token = tokenString.getString("access_token");
		System.out.println(access_token);
		String jsapi_ticket = commonUtil.getJsApiTicket(access_token);
		System.out.println(jsapi_ticket);
		Sign sign = new Sign();
		Map<String, String> params = sign.sign(jsapi_ticket, "url");
		JSONObject jsonObject = JSONObject.fromObject(params);
		String jsonStr = jsonObject.toString();
		System.out.println(jsonStr);
	}

	/**
	 * 调用微信JS接口的临时票据
	 * 
	 * @param access_token 接口访问凭证
	 * @return
	 */
	public static String getJsApiTicket(String access_token) {
		
		String requestUrl = titck_url.replace("ACCESS_TOKEN", access_token);
		System.out.println("调用微信JS接口的临时票据"+requestUrl);
		// 发起GET请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		String ticket = null;
		if (null != jsonObject) {
			try {
				ticket = jsonObject.getString("ticket");
			} catch (JSONException e) {
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}");
			}
		}
		return ticket;
	}
}