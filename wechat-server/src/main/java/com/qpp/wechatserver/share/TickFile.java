package com.qpp.wechatserver.share;

import net.sf.json.JSONObject;

import java.io.*;
import java.util.Date;

public class TickFile {
	private static String path = "../../openinf.txt";
	private static String pathtoken="../../openinfToken.txt";
	private static File file;
	private static File tokenfile;
	static{
		file = new File(path);
		System.out.println(pathtoken);
		tokenfile=new File(pathtoken);
		if(!file.exists()){
			try {
				System.out.println("创建文件!");
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!tokenfile.exists()){
			try {
				System.out.println("创建文件!");
				tokenfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void save(){
		saveTick(CrazyServlet.token_url,CommonUtil.titck_url,CrazyServlet.APPID,CrazyServlet.APPSECRET);
	}
	public static String getTicke() throws Exception{
		String[] ticks =  readDataFromFile(file);
		if(ticks!=null){
			if(ticks.length<2){
				System.out.println("小于二");
				save();
				return getNumber();
			};
			return getNumber();
		}else{
			save();
			return getNumber();
		}
		//return null;
	}
	public static String getToken() throws Exception{
		String[] token =  readDataFromFile(tokenfile);
		if(token!=null){
			if(token.length<2){
				System.out.println("小于二");
				saveT();
				return getTokenNumber();
			};
			return getTokenNumber();
		}else{
			saveT();
			return getTokenNumber();
		}
	}
	
	private static String getTokenNumber() {
		// TODO Auto-generated method stub
		try {
			String[] token =  readDataFromFile(tokenfile);
			boolean falg  = false;
			if(new Date().getTime()/1000-Long.parseLong(token[1])/1000>6000){
				saveT();
				//getTicke();
				falg = true;
			}
			if(falg){
				token =  readDataFromFile(tokenfile);
			}
			return token[0];
		} catch (IOException e) {
		}
		return null;
	}
	private static void saveT() {
		// TODO Auto-generated method stub
		saveToken(CrazyServlet.token_url,CommonUtil.titck_url,CrazyServlet.APPID,CrazyServlet.APPSECRET);
	}
	public static String getNumber(){
		try {
			String[] ticks =  readDataFromFile(file);
			boolean falg  = false;
			if(new Date().getTime()/1000-Long.parseLong(ticks[1])/1000>6000){
				save();
				//getTicke();
				falg = true;
			}
			if(falg){
				ticks =  readDataFromFile(file);
			}
			return ticks[0];
		} catch (IOException e) {
		}
		return null;
	}
	private static void saveToken(String token_url,String titck_url,String appid,String appsecret) {
		// TODO Auto-generated method stub
		//save(GetOpenIdController.token_url,GetOpenIdController.titck_url,GetOpenIdController.appid,GetOpenIdController.appsecret);
		String tokenurl = token_url.replaceAll("APPID",appid).replaceAll("APPSECRET",appsecret);
		JSONObject jsonObject = CommonUtil.httpsRequest(tokenurl, "GET", null);
		String token = "";
		if (null != jsonObject) {
			token = jsonObject.getString("access_token");
			System.out.println(token+"我是token");
			
			try {
				writeDataToFile(tokenfile,token+","+new Date().getTime());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void saveTick(String token_url,String titck_url,String appid,String appsecret){
		//String tokenurl = token_url.replaceAll("APPID",appid).replaceAll("APPSECRET",appsecret);
		//JSONObject jsonObject = CommonUtil.httpsRequest(tokenurl, "GET", null);
//		String token = "";
//		if (null != jsonObject) {
//			token = jsonObject.getString("access_token");
//			System.out.println(token+"我是token");
		try {
			String ticketurl = titck_url.replaceAll("ACCESS_TOKEN",TickFile.getToken());
			JSONObject ticke = CommonUtil.httpsRequest(ticketurl, "GET", null);
			String tic = null;
//			if (null != jsonObject){
				tic = ticke.getString("ticket");
				System.out.println(tic+"我是tic");
				
					writeDataToFile(file,tic+","+new Date().getTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
//			}
//		}
	}
	
	private static String[] readDataFromFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String str = "";
		// = null;
		while((str = reader.readLine())!=null){
			String[] stuInfo = str.split(",");
			return stuInfo;
		}
		
		return null;
	}
	
	private static void writeDataToFile(File file,String stu) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new FileOutputStream(file,false));
		out.println(stu.toString());
		out.close();
	}
}
