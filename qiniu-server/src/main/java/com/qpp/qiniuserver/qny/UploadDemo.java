package com.qpp.qiniuserver.qny;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;

@RefreshScope
public class UploadDemo {
	@Value("${qiniu.access_key}")
	private String access_key;
	@Value("${qiniu.secret_key}")
	private String secret_key;
	@Value("${qiniu.bucketname}")
	private String bucketname;
	@Value("${qiniu.httpurl}")
	private String httpurl;
	@Value("${qiniu.fops}")
	private String fops;
	@Value("${qiniu.pipeline}")
	private String pipeline;


	Auth auth = Auth.create(access_key, secret_key);
	//构造一个带指定Zone对象的配置类
	Configuration cfg = new Configuration(Zone.zone1());
	UploadManager uploadManager = new UploadManager(cfg);
//	String fops = "?imageView2/0/q/75|watermark/1/image/aHR0cDovL3Awb3FkNXM5dy5ia3QuY2xvdWRkbi5jb20vMTUxNzIxMDYyMjA0NUBxaW5ydWlkYS1RbVJzVnJqdC5wbmc=/dissolve/50/gravity/SouthEast/dx/10/dy/10|imageslim";
//	String fops = "-comic";
//	String pipeline = "cartoon";

	public String getUpToken(String filename) {
		// return auth.uploadToken(bucketname);
		// 可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
		String urlbase64 = UrlSafeBase64.encodeToString(filename);
		String pfops = fops + "|saveas/" + urlbase64;
		return auth.uploadToken(bucketname, null, 3600,
				new StringMap().putNotEmpty("persistentOps", pfops)
						.putNotEmpty("persistentPipeline", pipeline), true);
	}

	public String getUpToken() {
		return auth.uploadToken(bucketname);

	}

	/**
	 *    增加水印
	 *
	 * @author pengpai
	 * @date 2018/5/19 15:12
	 * @param file
	 * @return java.lang.String
	 */
	public String uploadWater(MultipartFile file) throws IOException {
		String url = "";
		try {
			Random random = new Random();
			byte[] buffer = file.getBytes();
			String date = new Date().getTime() + "@batlive-" + getRandomString(8);
			String extendName = file.getOriginalFilename().substring(
					file.getOriginalFilename().lastIndexOf("."));
			String filename = date + extendName;
			Response res = uploadManager.put(buffer, filename,
					getUpToken(filename));
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
			url = httpurl + filename+fops;
			return httpurl + filename+fops;
		} catch (QiniuException e) {
			Response r = e.response;
			System.out.println(r.toString());
			try {
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return url;
	}
	/**
	 *    不加水印
	 *
	 * @author pengpai
	 * @date 2018/5/19 15:12
	 * @param file
	 * @return java.lang.String
	 */
	public String upload(MultipartFile file) throws IOException {
		String url = "";
		try {
			Random random = new Random();
			byte[] buffer = file.getBytes();
			String date = new Date().getTime() + "@kaka-" + getRandomString(8);
			String extendName = file.getOriginalFilename().substring(
					file.getOriginalFilename().lastIndexOf("."));
			String filename = date + extendName;
			Response res = uploadManager.put(buffer, filename, getUpToken());
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
			url = httpurl + filename;
			return httpurl + filename;
		} catch (QiniuException e) {
			Response r = e.response;
			System.out.println(r.toString());
			try {
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return url;
	}

	/**
	 *    路径上传
	 *
	 * @author pengpai
	 * @date 2018/5/19 15:13
	 * @param path
	 * @return java.lang.String
	 */
	public String upload(String path) throws IOException {
		File file = new File(path);
		String url = "";
		try {
			Random random = new Random();
			// byte[] buffer = file.getBytes();
			String date = new Date().getTime() + "@kaka-"
					+ getRandomString(8);
			String extendName = file.getName().substring(
					file.getName().lastIndexOf("."));
			String filename = date + extendName;
			Response res = uploadManager.put(file, filename,
					getUpToken(filename));
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
			// uploadManager.put(file, key, token)
			url = httpurl + filename;
			System.err.println(url);
			return httpurl + filename;
		} catch (QiniuException e) {
			Response r = e.response;
			System.out.println(r.toString());
			try {
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return url;
	}

	/**
	 *    断点续上传(加水印)
	 *
	 * @author pengpai
	 * @date 2018/5/19 15:19
	 * @param file
	 * @return java.lang.String
	 */
	public String uploadPoint(MultipartFile file) throws IOException {
		String url = "";
		String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucketname).toString();
		try {
			//断点续传文件进度保存目录
			FileRecorder fileRecorder = new FileRecorder(localTempDir);
			UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
			Random random = new Random();
			byte[] buffer = file.getBytes();
			String date = new Date().getTime() + "@bat-" + getRandomString(8);
			String extendName = file.getOriginalFilename().substring(
					file.getOriginalFilename().lastIndexOf("."));
			String filename = date + extendName;
			Response res = uploadManager.put(buffer, filename, getUpToken(filename));
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
			url = httpurl + filename;
			return httpurl + filename;
		} catch (QiniuException e) {
			Response r = e.response;
			System.out.println(r.toString());
			try {
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return url;
	}

	public String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		UploadDemo up = new UploadDemo();
		try {
			String name = up.upload("D:\\777.png");
			System.err.println(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
