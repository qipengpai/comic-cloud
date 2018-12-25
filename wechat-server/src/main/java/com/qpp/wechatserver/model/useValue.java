package com.qpp.wechatserver.model;

public class useValue {

	/*测试地址*/
//	public final static String AppId ="wx80163f322bdcbb58";
//	public final static String AppSecret ="49e9b25492c0a9173c9ff78bbb054dbc";
	
	
	/*正式地址*/
	public final static String AppId ="wx053e2bdaaf81ab7a";
	public final static String AppSecret ="0b6f996c50e98740d89f29d395fe1aad";
	
	public final static String getTokenUrl ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public final static String getCodeUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	public final static String getOAuthAccessToken="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	public final static String getreferAccessUrl="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	public final static String getOAuthUserNews="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public final static String isOAuthAccessToken="https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
	
	
	
}
