package com.qpp.wechatserver.system;

public class SystemConfig {
	/*威富通支付*/
	public static final String _WFT_APPID="151503546290585";
	public static final String _WFT_KEY="e90e68f5f4d11c1b9eb627722eb19aef";//e8caff9fd6e7a775ecc5924c54498372 2efe864be58fe620c5accddc6fa5179b
	public static final String _WFT_MCH_ID="101530818387";//102515489404 101520257318
	public static final String _WFT_POST_URL="https://pay.swiftpass.cn/pay/gateway";
	public static final String _WFT_NOTIFY_URL="http://www.kakamanhua.com/Comic/pay/callback/playPoint/ByWFT.do";
	
	
	// 花花 聚合支付 参数配置
	public static final String JH_CHANNELID = "1020";// 支付类别
	public static final String JH_APPID = "wx0e0ea8f7955b1fcc";// APPID或者扫码网址
	public static final String JH_P_USERID = "2295";// 商户ID
	public static final String JH_P_FACETYPE = "CNY";// 币种
	public static final String JH_SALFSTR = "7h9b0a1n8IXCFzABTmaRcE1QMhTSwGLo";// 密钥
	// 更新地址
	public static final String UPDATEVIP = "http://ptest.edisonluorui.com/Comic/app/Check/vip.do";
	// 充值奖励
	public static final Integer payDayIntegral7 = 1;
	public static final int payFirstIntegral = 1;
	public static final int shareComicIntegral = 1;
	public static final int seeMeIntegral = 1;
	public static final int boundPhoneIntegral = 1;
	public static final int payDayshare = 1;
	public static final int shareDayCartoon = 1;

	// 七牛云配置
	public static final String QN_ACCESS_KEY = "k_ag52XwtjTrXCNHnIjdfTyCC4B76c-sS2iahSL0";
	public static final String QN_SECRET_KEY = "D-ftap1MDThqgETokAOMcUeqwnUvILT6mJY-D_1X";
	public static final String QN_bucketname = "cartoon";
	public static final String QN_httpurl = "http://p3b8r8j4s.bkt.clouddn.com/";

	/* 现在支付支付信息配置 */
	public static final String _XZZF_APPID = "151184124117180";
	public static final String _XZZF_APPID_KEY = "teHju6oF2kDzkLUK31UBfgkC0udS4kLI";
	public static final String _XZZF_FUNCTION_CODE = "WP001";// 功能码
	public static final String _XZZF_VARSION_NUM = "1.0.0";// 版本号
	public static final String _XZZF_FORMAT = "0";// 输出格式
	public static final String _XZZF_CHANNEL = "13";// 渠道号
	public static final String _XZZF_EQUIPMENT = "0600";// 设备类型
	public static final String _XZZF_SIGNTYPE = "MD5";// 签名类型
	public static final String _XZZF_MHTCURRENCYTYPE = "156";
	public static final String _XZZF_MHTORDERTYPE = "01";
	public static final String _XZZF_MHTCHARSET = "UTF-8";

	public static final String DOMAIN_NAME = "http://www.edisonluorui.com/";
	public static final String _TEST_DOMAIN_NAME = "http://lgshjzsc.edisonluorui.com/";

	// 短信
	public static final String SendCodeAppkey = "key-abfdf6a042ca56ab23f934c66c2d2b53";
	public static final String SendCodeHttpUrl = "http://sms-api.luosimao.com/v1/send.json";

	public static final String SendCodeMessage(String code) {
		return "验证码" + code + "。[泄露有风险，如非本人操作，请忽略本条信息。]【咔咔漫画】";
	}

	public static final String SendCodeMessageByTZ(String code) {
		return "通知:" + code + "【潮人微游】";
	}

	public static final String SendCodeMessage_update_pwd(String code) {
		return "找回密码验证码:" + code + "【潮人微游】";
	}

	// 默认头像
	public static final String userimg = "http://ox143yv1l.bkt.clouddn.com/20170929145712.jpg";
	// 提供商上传文件
	public static final String ProviderFile = "/admin/upload/authentication/";
	// 游戏图标物理位置
	public static final String Game_Head = "/admin/upload/Game/head/";
	// 游戏介绍图物理位置
	public static final String Game_Boyd = "/admin/upload/Game/body/";

	public static final String WeiXin_FX = "http://www.edisonluorui.com/AppGame/servlet/wx/fx/user/gameinfo.do";

	public static final String weixlogin = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0e0ea8f7955b1fcc&redirect_uri=http%3A%2F%2Fwww.edisonluorui.com%2FAppGame%2Fservlet%2FOAuthAPIServlet.do&response_type=code&scope=snsapi_userinfo&state=state#wechat_redirect";

	public static String SendCode_buy_mall(String mallInfoId, String orderDate,
			String mallInfoName, String orderSize, String phone) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("订单号为: " + mallInfoId + "   于 :" + orderDate
				+ "  在潮人商城订购商品:  《" + mallInfoName + "》     数量: *  "
				+ orderSize + "  充值号码为 : " + phone + "  请及时完成订单");
		return sb.toString();
	}

	public static String SendCode_buy_true_mall(String orderNum,
			String getdate_yyyy_MM_dd_Hms, String orderDescription,
			String string) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("订单号为: " + orderNum + "   于 :" + getdate_yyyy_MM_dd_Hms
				+ "  在潮人商城订购商品:  《" + orderDescription + "》     数量: *  "
				+ string + "  请及时完成订单");
		return sb.toString();
	}

	public static String SendCode_buy_true_mall_index(
			String getdate_yyyy_MM_dd_Hms, String orderDescription,
			String string) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("提醒您于 :" + getdate_yyyy_MM_dd_Hms + "潮人商城的商品:《"
				+ orderDescription + "》剩余数量已不足:*" + string + "请保持商品数量在可控范围");
		return sb.toString();
	}
}
