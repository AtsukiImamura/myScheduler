package scheduler;

import java.util.List;

import scheduler.bean.ProjectBean;
import scheduler.facade.ProjectBeanFacade;


public class FuncTest {


	public static void main(String[] args) throws Exception{
/*

	    String TARGET_HOST = "https://tonkotsu-ohmoon.ssl-lolipop.jp/myscheduler/data_test.php";
	    HttpClient httpclient = null;
	    HttpPost post = null;
	    HttpEntity entity = null;

	    int socketTimeout = 5500;
	    int connectionTimeout = 1500;

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
        Map<String,Object> requestInfo = new HashMap<String,Object>();
        requestInfo.put("db_name", "T_PROJECT");
        requestInfo.put("request_type", "T_PROJECT");
        attributes.put("request_info", requestInfo);

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

        if (entity == null) {
        	return;
        }

        String json  = EntityUtils.toString(entity);

        JsonObject jsonResponse = (JsonObject) new Gson().fromJson(json, JsonObject.class);
        JsonArray jsonData = jsonResponse.get("data").getAsJsonArray();
*/
/*
        JsonArray jsonData = DatabaseUtil.findData("T_PROJECT", "test_001", "test");
        for(JsonElement element :jsonData ){
        	System.out.println("* "+element.toString());
        }
*/

		ProjectBeanFacade projectFacade = new ProjectBeanFacade();
		List<ProjectBean> projectBeanList = projectFacade.findAll();

		projectFacade.insert(projectBeanList.get(0));

//        EntityUtils.consume(entity);
//        post.abort();
		System.out.println("");


	}
}
