package com.qpp.wechatserver.dao.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qpp.wechatserver.basics.entity.UserEntity;
import com.qpp.wechatserver.dao.WeiXinDao;
import com.qpp.wechatserver.method.HttpsUtil;
import com.qpp.wechatserver.model.OAuthAccessToken;
import com.qpp.wechatserver.model.useValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;


public class WeiXinDaoImpl implements WeiXinDao{
	private Logger log = LoggerFactory.getLogger(WeiXinDaoImpl.class);
	/**
	 * 微信OAuth2.0授权（目前微信只支持在微信客户端发送连接，实现授权）
	 * */
	public String getCodeUrl(String appid, String redirect_uri,String scope,String state) throws Exception {
		redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
		String getCodeUrl=useValue.getCodeUrl.replace("APPID", appid).replace("REDIRECT_URI", redirect_uri).replace("SCOPE", scope).replace("STATE", state);
		log.info("getCodeUrl Value:"+getCodeUrl);
		return getCodeUrl;
	}
	/**
	 * 微信OAuth2.0授权-获取accessToken(这里通过code换取的网页授权access_token,与基础支持中的access_token不同）
	 */
	public OAuthAccessToken getOAuthAccessToken(String appid, String secret,String code) throws Exception {
		// 替换字符串，获得请求access token URL
		String tokenUrl=useValue.getOAuthAccessToken.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
		log.info("getOAuthAccessToken Value:"+tokenUrl);
		// 通过https方式请求获得web_access_token
		String response = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);
		JSONObject jsonObject = JSON.parseObject(response);
		log.info("请求到的Access Token:{}", jsonObject.toJSONString());
		OAuthAccessToken accessToken=new OAuthAccessToken();
		accessToken.setAccessToken(jsonObject.getString("access_token"));
		accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
		accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
		accessToken.setOpenid(jsonObject.getString("openid"));
		accessToken.setScope(jsonObject.getString("scope"));
		return accessToken;
	}
	/**
	 * 微信OAuth2.0授权-刷新access_token（如果需要）
	 */
	public OAuthAccessToken refershOAuthAccessToken(String appid, String refresh_token) throws Exception {
		String getreferAccessUrl=useValue.getreferAccessUrl.replace("APPID", appid).replace("REFRESH_TOKEN", refresh_token);
		log.info("getreferAccessUrl Value:"+getreferAccessUrl);
		String response = HttpsUtil.httpsRequestToString(getreferAccessUrl, "GET", null);
		JSONObject jsonObject = JSON.parseObject(response);
		log.info("请求到的Access Token:{}", jsonObject.toJSONString());
		OAuthAccessToken accessToken=new OAuthAccessToken();
		accessToken.setAccessToken(jsonObject.getString("access_token"));
		accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
		accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
		accessToken.setOpenid(jsonObject.getString("openid"));
		accessToken.setScope(jsonObject.getString("scope"));
		return accessToken;
	}
	/**
	 * 微信OAuth2.0授权-检验授权凭证（access_token）是否有效
	 */
	public boolean isAccessTokenValid(String accessToken, String openId) throws Exception {
		String isOAuthAccessToken=useValue.isOAuthAccessToken.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		log.info("isOAuthAccessToken Value:"+isOAuthAccessToken);
		String response = HttpsUtil.httpsRequestToString(isOAuthAccessToken, "GET", null);
		JSONObject jsonObject = JSON.parseObject(response);
		log.info("请求到的Access Token:{}", jsonObject.toJSONString());
		int returnNum=jsonObject.getIntValue("errcode");
		if(returnNum==0){
			return true;
		}
		return false;
	}
	/**
	 * 微信OAuth2.0授权-获取用户信息（网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息）
	 */
	public UserEntity acceptOAuthUserNews(String accessToken, String openId) throws Exception {
		String getOAuthUserNews=useValue.getOAuthUserNews.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		log.info("getOAuthUserNews Value:"+getOAuthUserNews);
		String userMessageResponse = HttpsUtil.httpsRequestToString(getOAuthUserNews, "GET", null);
		JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);
		log.info("用户信息:{}", userMessageJsonObject.toJSONString());
		JSONObject jsonObject = JSON.parseObject(userMessageResponse);
		UserEntity entity=new UserEntity();
		entity.setOpenid(jsonObject.getString("openid"));
		entity.setNickname(jsonObject.getString("nickname"));
		entity.setSex(jsonObject.getIntValue("sex"));
		entity.setProvince(jsonObject.getString("province"));
		entity.setCity(jsonObject.getString("city"));
		entity.setCountry(jsonObject.getString("country"));
		entity.setHeadimgurl(jsonObject.getString("headimgurl"));
		log.info(jsonObject.toJSONString());
//		entity.setSubscribe(jsonObject.getInteger("subscribe"));
//		entity.setSubscribe_time(jsonObject.getString("subscribe_time"));
//		entity.setUnionid(jsonObject.getString("unionid"));
		return entity;
	}
	
}
