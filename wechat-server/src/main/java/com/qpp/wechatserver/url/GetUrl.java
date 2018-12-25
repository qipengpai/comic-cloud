package com.qpp.wechatserver.url;

import com.qpp.wechatserver.dao.impl.WeiXinDaoImpl;
import com.qpp.wechatserver.model.useValue;


public class GetUrl {
	
	
	public static String getWXGameUrl(String str){
		WeiXinDaoImpl dao=new WeiXinDaoImpl();
		String pathUrl=null;
		try {
			pathUrl=dao.getCodeUrl(useValue.AppId,str,"snsapi_userinfo", "state");
			System.out.println(pathUrl);
		} catch (Exception e) {
			
		}
		return pathUrl;
	}

	public static String getComicUrl(String cartoonId,String cartoonSetId,String qd){
	String url="http://www.kktoon.com/htd/#/login?cartoonId="+cartoonId+"&cartoonSetId="+cartoonSetId+"&qd="+qd+"&moneyState=0";
		WeiXinDaoImpl dao=new WeiXinDaoImpl();
		String pathUrl=null;
		try {
			pathUrl=dao.getCodeUrl(useValue.AppId,url,"snsapi_userinfo", "state");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathUrl;
	}
	public static String getComicMainUrl(String qd){
		String url="http://www.kktoon.com/htd/#/login?qd="+qd;
		WeiXinDaoImpl dao=new WeiXinDaoImpl();
		String pathUrl=null;
		try {
			pathUrl=dao.getCodeUrl(useValue.AppId,url,"snsapi_userinfo", "state");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathUrl;
	}
	
	public static void main(String[] args){
//		String url="http://www.kktoon.com/#/cartoonimg?id=4lpFjWbqfopp0SpQsumncyUUa8zgcCKY&cartoonId=geEcrUftKIRgeoEGe99MPnOsVwfy1IWg&moneyState=0&sort=1&ts=1";
	//	String url="http://www.kktoon.com/htd/#/login?cartoonSetId=vFrQrH1qKORARjcCGkCglqTxgQU4ZGz5&cartoonId=cjJbaVv35tcXK5WYZ1U1eutjGufUUAo2&moneyState=0";
		
//		String url="http://www.kktoon.com/UserLoginWX.html?qd=PT";
		String url="http://www.kktoon.com/htd/#/login?qd=DBNGJ";
		WeiXinDaoImpl dao=new WeiXinDaoImpl();
		String pathUrl=null;
		try {
			pathUrl=dao.getCodeUrl(useValue.AppId,url,"snsapi_userinfo", "state");
			System.out.println(pathUrl);
		} catch (Exception e) {
			
		}
		System.out.println(pathUrl);
	}
}
