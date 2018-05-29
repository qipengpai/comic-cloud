package com.qpp.wechatserver.dao;

import com.qpp.wechatserver.basics.entity.UserEntity;
import com.qpp.wechatserver.model.OAuthAccessToken;


public interface WeiXinDao {
	/**
	 * 微信OAuth2.0授权（目前微信只支持在微信客户端发送连接，实现授权）
	 * */
	public String getCodeUrl(String appid, String redirect_uri, String scope, String state) throws Exception;
	/**
	 * 微信OAuth2.0授权-获取accessToken(这里通过code换取的网页授权access_token,与基础支持中的access_token不同）
	 */
	public OAuthAccessToken getOAuthAccessToken(String appid, String secret, String code) throws Exception;
	/**
	 * 微信OAuth2.0授权-刷新access_token（如果需要）
	 */
	public OAuthAccessToken refershOAuthAccessToken(String appid, String refresh_token) throws Exception;
	/**
	 * 微信OAuth2.0授权-检验授权凭证（access_token）是否有效
	 */
	public boolean isAccessTokenValid(String accessToken, String openId) throws Exception;
	/**
	 * 微信OAuth2.0授权-获取用户信息（网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息）
	 */
	public UserEntity acceptOAuthUserNews(String accessToken, String openId) throws Exception;
}
