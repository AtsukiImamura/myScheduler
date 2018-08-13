package scheduler.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import scheduler.common.constant.NameConstant;

public class DatabaseUtil {

    private static int socketTimeout = 2500;
    private static int connectionTimeout = 8000;


    /**
     * データを取得する
     * @param requestType
     * @param userCode
     * @param password
     * @return
     * @throws Exception
     */
	public static JsonArray findData(String requestType,String userCode,String password) throws Exception{

		long start = System.currentTimeMillis();
		System.out.printf("**** findData start ****\n");

	    HttpPost post = null;
	    HttpEntity entity = null;

	    HttpClient httpclient = getInitializedHttpClient();
        post = getInitializedHttpPost(requestType, userCode, password,NameConstant.DATABASE_URL_GET_DATA);

        System.out.printf("         execute : %.2f\n",(double)(System.currentTimeMillis() - start));
        HttpResponse response = httpclient.execute(post);

        System.out.printf("         received : %.2f\n",(double)(System.currentTimeMillis() - start));

        if(response.getStatusLine().getStatusCode() != 200 ){
            System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
            return null;
        }

        entity = response.getEntity();

        if (entity == null) {
        	return null;
        }

        System.out.printf("         get json start : %.2f\n",(double)(System.currentTimeMillis() - start));
        String json  = EntityUtils.toString(entity);

        JsonObject jsonResponse = (JsonObject) new Gson().fromJson(json, JsonObject.class);
        JsonArray jsonData = jsonResponse.get("data").getAsJsonArray();

        EntityUtils.consume(entity);
        post.abort();

        System.out.printf("**** findData finish **** : %.2f\n\n",(double)(System.currentTimeMillis() - start));
        return jsonData;

	}

	/**
	 * データを挿入する
	 * @param requestType
	 * @param userCode
	 * @param password
	 * @param data
	 * @return
	 */
	public static boolean insert(String requestType,String userCode,String password,Map<String,Object> data,List<String> primaryKeys){
		DatabaseUtil.send(NameConstant.DATABASE_URL_INSERT_DATA, requestType, userCode, password, data, primaryKeys);
		//TODO 結果によるエラーハンドリング
		return true;
	}


	/**
	 * データをアップデートする
	 * @param requestType
	 * @param userCode
	 * @param password
	 * @param data
	 * @return
	 */
	public static boolean update(String requestType,String userCode,String password,Map<String,Object> data,List<String> primaryKeys){
		DatabaseUtil.send(NameConstant.DATABASE_URL_UPDATE_DATA, requestType, userCode, password, data, primaryKeys);
		//TODO 結果によるエラーハンドリング
		return true;
	}





	private static boolean send(String url,String requestType,String userCode,String password,Map<String,Object> data,List<String> primaryKeys){
	    HttpEntity entity = null;

	    HttpClient httpclient = getInitializedHttpClient();

	    Map<String,Object> attributes = DatabaseUtil.getBasicAttributeMap(requestType, userCode, password);
	    attributes.put("data", data);
	    attributes.put("primary_keys", primaryKeys);
	    HttpPost post;
	    HttpResponse response = null;
		try {
			post = DatabaseUtil.getInitializedHttpPost(attributes,url);
			response = httpclient.execute(post);
		} catch ( IOException e) {
			e.printStackTrace();
			return false;
		}

        if(response.getStatusLine().getStatusCode() != 200 ){
            System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
            return false;
        }

        entity = response.getEntity();

        String json;
		try {
			json = EntityUtils.toString(entity);
			JsonObject jsonResponse = (JsonObject) new Gson().fromJson(json, JsonObject.class);
			//TODO 返されたデータで挿入が成功したかどうかが分かる


		} catch (Exception e) {
			e.printStackTrace();
		}


		return true;
	}



	/**
	 * 接続に使うHttpClientを返す
	 * @return
	 */
	private static HttpClient getInitializedHttpClient(){


	    RequestConfig requestConfig = RequestConfig.custom()
	    	      .setConnectTimeout(connectionTimeout)
	    	      .setSocketTimeout(socketTimeout)
	    	      .build();


	    HttpClient httpclient = HttpClientBuilder.create()
        			.setDefaultRequestConfig(requestConfig)
        			.build();

        return httpclient;
	}


	/**
	 * APIリクエストに必須の属性をセットしたマップを返す
	 * @param requestType リクエストのタイプ
	 * @param userCode ユーザーコード
	 * @param password パスワード
	 * @return
	 */
	private static Map<String,Object> getBasicAttributeMap(String requestType,String userCode,String password){
		Map<String,Object> attributes = new HashMap<String,Object>();
	    Map<String,Object> userInfo = new HashMap<String,Object>();
	    userInfo.put("user_code", userCode);
	    userInfo.put("password",password);
	    attributes.put("user_info", userInfo);
	    Map<String,Object> requestInfo = new HashMap<String,Object>();
	    requestInfo.put("request_type", requestType);
	    attributes.put("request_info", requestInfo);

	    return attributes;
	}


	/**
	 * APIリクエストに必須の属性をセットしたHttpPostを返す
	 * @param requestType リクエストのタイプ
	 * @param userCode ユーザーコード
	 * @param password パスワード
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static HttpPost getInitializedHttpPost(String requestType,String userCode,String password,String url) throws UnsupportedEncodingException{
		Map<String,Object> attributes = getBasicAttributeMap(requestType, userCode, password);
        return getInitializedHttpPost(attributes,url);
	}

	/**
	 * 指定された属性をセットしたHttpPostを返す
	 * @param attributes 属性のマップ
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static HttpPost getInitializedHttpPost(Map<String,Object> attributes,String url) throws UnsupportedEncodingException{
		Gson gson = new Gson();
	    String jsonAttributes = gson.toJson(attributes);

	    System.out.println(jsonAttributes);
	    HttpPost post =  new HttpPost(url);
	    post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

	    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("request_json",jsonAttributes));
	    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

	    return post;
	}
}
