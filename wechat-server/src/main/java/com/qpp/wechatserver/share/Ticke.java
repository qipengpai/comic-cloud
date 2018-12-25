package com.qpp.wechatserver.share;

public class Ticke {
	private String jsapiTicket;
	private String timeStamp;
	private String nonceStr;
	private String signaTure;
	private String url;
	private String appId;

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignaTure() {
		return signaTure;
	}

	public void setSignaTure(String signaTure) {
		this.signaTure = signaTure;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "Ticke [jsapiTicket=" + jsapiTicket + ", timeStamp=" + timeStamp + ", nonceStr=" + nonceStr + ", signaTure=" + signaTure + ", url=" + url + "]";
	}

}
