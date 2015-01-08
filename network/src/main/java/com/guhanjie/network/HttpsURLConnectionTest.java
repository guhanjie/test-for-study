package com.guhanjie.network;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

public class HttpsURLConnectionTest {

    private String url = "https://openapi.youku.com/v2/oauth2/authorize?client_id=cc55ef664c3a7540&response_type=code&redirect_uri=http%3A%2F%2Fyun.glodon.com&state=xyz";

    private myX509TrustManager xtm = new myX509TrustManager();

    private myHostnameVerifier hnv = new myHostnameVerifier();

    public HttpsURLConnectionTest() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS"); //或SSL
            X509TrustManager[] xtmArray = new X509TrustManager[] {xtm};
            sslContext.init(null, xtmArray, new java.security.SecureRandom());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);
    }

    public void run() {
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection)(new URL(url)).openConnection();
//            urlCon.setDoOutput(true);
//            urlCon.setRequestMethod("POST");
//            urlCon.setRequestProperty("Content-Length", "1024");
//            urlCon.setUseCaches(false);
//            urlCon.setDoInput(true);
//            urlCon.getOutputStream().write("request content".getBytes("gbk"));
//            urlCon.getOutputStream().flush();
//            urlCon.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            // 增加自己的代码
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HttpsURLConnectionTest httpsTest = new HttpsURLConnectionTest();
        httpsTest.run();
    }
}

/**
 * 重写三个方法
 * @author Administrator
 *
 */
class myX509TrustManager implements X509TrustManager {
    
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        System.out.println("cert: " + chain[0].toString() + ", authType: " + authType);
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}

/**
 * 重写一个方法
 * @author Administrator
 *
 */
class myHostnameVerifier implements HostnameVerifier {

    public boolean verify(String hostname, SSLSession session) {
        System.out.println("Warning: URL Host: " + hostname + " vs. " + session.getPeerHost());
        return true;
    }
}