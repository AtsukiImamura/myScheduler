package scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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


public class FuncTest {


	public static void main(String[] args) throws IOException{


	    String TARGET_HOST = "https://tonkotsu-ohmoon.ssl-lolipop.jp/myscheduler/data_test.php";
	    HttpClient httpclient = null;
	    HttpPost post = null;
	    HttpEntity entity = null;

	    int socketTimeout = 5500;
	    int connectionTimeout = 800;

	    RequestConfig requestConfig = RequestConfig.custom()
	    	      .setConnectTimeout(connectionTimeout)
	    	      .setSocketTimeout(socketTimeout)
	    	      .build();


        httpclient = HttpClientBuilder.create()
        			.setDefaultRequestConfig(requestConfig)
        			.build();


        post = new HttpPost(TARGET_HOST);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        Map<String,Object> attributes = new HashMap<String,Object>();
        Map<String,Object> userInfo = new HashMap<String,Object>();
        userInfo.put("user_code", "test_001");
        userInfo.put("password", "test");
        attributes.put("user_info", userInfo);

        Gson gson = new Gson();
        String jsonAttributes = gson.toJson(attributes);

        System.out.println(jsonAttributes);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("request_json",jsonAttributes));
        post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        HttpResponse response = httpclient.execute(post);

        if(response.getStatusLine().getStatusCode() != 200 ){

            System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
            return;
        }

        entity = response.getEntity();

        if (entity != null) {

            System.out.println(EntityUtils.toString(entity));
            EntityUtils.consume(entity);
            post.abort();
        }

        System.out.println("finish");


	}
}
