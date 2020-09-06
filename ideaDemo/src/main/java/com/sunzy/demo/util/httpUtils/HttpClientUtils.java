package com.sunzy.demo.util.httpUtils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
public class HttpClientUtils {
    private static final int REQUEST_TIMEOUT = 5 * 1000;// 设置请求超时10秒钟
    private static final int SO_TIMEOUT = 10 * 1000; // 设置等待数据超时时间10秒钟
    // static ParseXml parseXML = new ParseXml();
    // 初始化HttpClient，并设置超时
    public static HttpClient getHttpClient() {
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpParams);
        return client;
    }
    public static boolean doPost(String url) throws Exception {
        HttpClient client = getHttpClient();
        HttpPost httppost = new HttpPost(url);
        HttpResponse response;
        response = client.execute(httppost);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            return true;
        }
        client.getConnectionManager().shutdown();
        return false;
    }
    /**
     * 与远程交互的返回值post方式
     *
     * @param hashMap
     * @param url
     * @return
     */
    public static String getHttpXml(HashMap<String, String> hashMap, String url) {
        String responseMsg = "";
        HttpPost request = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> iter = hashMap.entrySet()
                .iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpClient client = HttpClientUtils.getHttpClient();
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                responseMsg = EntityUtils.toString(response.getEntity());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseMsg;
    }
    /**
     * map转字符串 拼接参数
     *
     * @param hashMap
     * @return
     */
    public static String mapToString(HashMap<String, String> hashMap) {
        String parameStr = "";
        Iterator<Map.Entry<String, String>> iter = hashMap.entrySet()
                .iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            parameStr += "&" + entry.getKey() + "=" + entry.getValue();
        }
        if (parameStr.contains("&")) {
            parameStr = parameStr.replaceFirst("&", "?");
        }
        return parameStr;
    }
}
