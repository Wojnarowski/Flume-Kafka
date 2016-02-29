package ElasticSearch.test;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;

public class ESFactory {

	private volatile  static JestHttpClient client;
	
	private ESFactory(){
		
	}
	
	public static JestHttpClient getClient(){
		 //先检查实例是否存在，如果不存在才进入下面的同步块  
		if(client ==null){
	     //同步块，线程安全的创建实例
			synchronized (ESFactory.class) {
				if(client ==null){
					JestClientFactory factory = new JestClientFactory();
		            factory.setHttpClientConfig(new HttpClientConfig.Builder(
		                    "http://localhost:8200").multiThreaded(true).build());
		            client = (JestHttpClient) factory.getObject();
				}
			}
		}
		return client;
	}
	
	public static void main(String[] args) {
        JestHttpClient client = ESFactory.getClient();
        System.out.println(client.getAsyncClient());
        System.out.println(client.getServers());
        client.shutdownClient();
    }
	
}
