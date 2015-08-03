package com.guhanjie.network.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;

public class HttpClientDemo {
	public static String url = "https://openapi.youku.com/v2/oauth2/authorize?client_id=cc55ef664c3a7540&response_type=code&redirect_uri=http%3A%2F%2Fyun.glodon.com&state=xyz";

	public static void main(String[] args) throws Exception {
		String code = postHTML();
		getAccessToken(code);
	}
	
	public static void getHTML() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity);
        //获取状态行  
        System.out.println(response.getStatusLine());  
        //返回内容  
        System.out.println(html);  
        httpclient.close();
    }
	
	public static String postHTML() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
        //优酷的授权网址
		HttpPost httppost = new HttpPost("https://openapi.youku.com/v2/oauth2/authorize_submit");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("client_id", "cc55ef664c3a7540"));
        params.add(new BasicNameValuePair("response_type","code"));
        params.add(new BasicNameValuePair("state","xyz"));
        params.add(new BasicNameValuePair("redirect_uri","http://yun.glodon.com"));
        params.add(new BasicNameValuePair("auth_type","1"));
        params.add(new BasicNameValuePair("account","glodon_isv@163.com"));
        params.add(new BasicNameValuePair("password","123qwe!@#"));
        httppost.setEntity(new UrlEncodedFormEntity(params));  
        //提交登录数据
        HttpResponse re = httpclient.execute(httppost);
        //获得跳转的网址
        Header locationHeader = re.getFirstHeader("Location"); 
        System.out.println(locationHeader);
        String code = locationHeader.getElements()[0].getValue();
        code = code.substring(0, code.indexOf("&state"));
        System.out.println(code);
        return code;
	}
	
	public static void getAccessToken(String code) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
        //优酷的授权网址
		HttpPost httppost = new HttpPost("https://openapi.youku.com/v2/oauth2/token");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("client_id", "cc55ef664c3a7540"));
        params.add(new BasicNameValuePair("client_secret","42b7a799e33f108ed308ade60f19e90a"));
        params.add(new BasicNameValuePair("grant_type","authorization_code"));
        params.add(new BasicNameValuePair("code",code));
        params.add(new BasicNameValuePair("redirect_uri","http://yun.glodon.com"));
        httppost.setEntity(new UrlEncodedFormEntity(params));  
        //提交登录数据
        HttpResponse response = httpclient.execute(httppost);
        //获得跳转的网址
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity);
        //获取状态行  
        System.out.println(response.getStatusLine());  
        //返回内容  
        System.out.println(html);  
        httpclient.close();
	}

}
