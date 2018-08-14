package scheduler;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class FuncTest2 {



	public static void main(String[] args) throws Exception{


		System.out.printf("FuncTest start\n");
		long start = System.currentTimeMillis();


		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.ndl.go.jp/");
		CloseableHttpResponse response1 = httpclient.execute(httpGet);


		try {
		    System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
			System.out.printf("         finish : %.1f\n",(double)(System.currentTimeMillis() - start));


		    System.out.println(EntityUtils.toString(entity1));


		    EntityUtils.consume(entity1);

		} finally {
		    response1.close();
		}
	}

}
