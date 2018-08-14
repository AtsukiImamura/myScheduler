package scheduler;

import java.io.File;

public class FuncTest {


	public static void main(String[] args) throws Exception{



//		List<ProjectBean> projectList = facade.findAll(ProjectBean.class);
//
//		ProjectBean project = projectList.get(0);
//
//		project.setDetail("piyo");
//		facade.update(project);

//		Map<String,String> keys = new HashMap<String,String>();
//		keys.put("PROJECT_CODE", "P_002");
//
//		String filePath = "T_PROJECT"+".csv";
//		CSVReader reader = new CSVReader(filePath);
//
//		String[] record = reader.one(keys);
//		record[1] = "hogehoge";
//		List<String> pKeys = new ArrayList<String>();
//		pKeys.add("PROJECT_CODE");
//		reader.update(pKeys, record);
//		AttributeSelectionBeanFacade facade = new AttributeSelectionBeanFacade();
//		facade.createNewDatabase();
//
		File file = new File("");
		System.out.println(file.getAbsoluteFile().getParentFile().getAbsolutePath());

		String num = "001";
		int no = Integer.parseInt(num);
		System.out.println(no);




		/*

		System.out.printf("FuncTest start\n");
		long start = System.currentTimeMillis();

//		String TARGET_HOST = "https://tonkotsu-ohmoon.ssl-lolipop.jp/myscheduler/update_data.php";
//	    String TARGET_HOST = "https://tonkotsu-ohmoon.ssl-lolipop.jp/myscheduler/insert_data.php";
	    String TARGET_HOST = "https://tonkotsu-ohmoon.ssl-lolipop.jp/myscheduler/data_test.php";
	    HttpClient httpclient = null;
	    HttpPost post = null;
	    HttpEntity entity = null;

	    int socketTimeout = 3300;
	    int connectionTimeout =300;

	    RequestConfig requestConfig = RequestConfig.custom()
	    	      .setConnectTimeout(connectionTimeout)
	    	      .setSocketTimeout(socketTimeout)
	    	      .build();

		System.out.printf("         check1 : %.1f\n",(double)(System.currentTimeMillis() - start));


        httpclient = HttpClientBuilder.create()
        			.setDefaultRequestConfig(requestConfig)
        			.build();


		System.out.printf("         check2 : %.1f\n",(double)(System.currentTimeMillis() - start));

        post = new HttpPost(TARGET_HOST);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");


		System.out.printf("         http prepared : %.1f\n",(double)(System.currentTimeMillis() - start));

        Map<String,Object> attributes = new HashMap<String,Object>();
        Map<String,Object> userInfo = new HashMap<String,Object>();
        userInfo.put("user_code", "test_001");
        userInfo.put("password", "test");
        attributes.put("user_info", userInfo);
        Map<String,Object> requestInfo = new HashMap<String,Object>();
        requestInfo.put("db_name", "T_PROJECT");
        requestInfo.put("request_type", "T_PROJECT");
        attributes.put("request_info", requestInfo);

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("PROJECT_CODE", "prj001");
        data.put("TASK_CODE", "T-007");
        data.put("START_AT", "2018-08-09 00:00:00");
        data.put("FINISH_AT", "2018-08-11 00:00:00");
        data.put("TASK_NAME", "hogehoge");
        data.put("CREATED_BY", "test_001");
        attributes.put("data", data);
        List<String> primaryKeys = new ArrayList<String>();
        primaryKeys.add("PROJECT_CODE");
        primaryKeys.add("TASK_CODE");
        attributes.put("primary_keys", primaryKeys);


        Gson gson = new Gson();
        String jsonAttributes = gson.toJson(attributes);

        System.out.println(jsonAttributes);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("request_json",jsonAttributes));
        post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        HttpResponse response;


		System.out.printf("         execute : %.1f\n",(double)(System.currentTimeMillis() - start));
        try{
        	response = httpclient.execute(post);
        }catch(Exception e){
        	post.abort();
    		System.out.printf("         ERROR : %.1f\n",(double)(System.currentTimeMillis() - start));
        	throw e;
        }
		System.out.printf("         received : %.1f\n",(double)(System.currentTimeMillis() - start));

        if(response.getStatusLine().getStatusCode() != 200 ){

            System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
            return;
        }

        entity = response.getEntity();

        if (entity == null) {
        	return;
        }

        String json  = EntityUtils.toString(entity);
        System.out.println("* "+json);
        */

        /*
        JsonObject jsonResponse = (JsonObject) new Gson().fromJson(json, JsonObject.class);
        JsonArray jsonData = jsonResponse.get("data").getAsJsonArray();
        //System.out.println("* "+jsonData);

        //JsonArray jsonData = DatabaseUtil.findData("T_PROJECT", "test_001", "test");
        for(JsonElement element :jsonData ){
        	System.out.println("* "+element.toString());
        }
        */



//        EntityUtils.consume(entity);
//        post.abort();


//		ProjectBeanFacade projectFacade = new ProjectBeanFacade();
//		List<ProjectBean> projectBeanList = projectFacade.findAll();
//
//		ProjectBean bean = projectBeanList.get(0);
//		bean.setProjectCode("P-002");
//
//		projectFacade.insert(bean);

	}
}
