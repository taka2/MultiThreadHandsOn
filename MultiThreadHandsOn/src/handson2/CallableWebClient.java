package handson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CallableとFutureを使って、
 * メインスレッドとは別のスレッドでwebサーバへのリクエストを行い、
 * 結果をメインスレッドで受け取り表示するサンプル。
 */
public class CallableWebClient {
	private static final int NUM_THREADS = 100;
	private static final String TARGET_URL = "http://localhost:8888";

	public static void main(String[] args) throws IOException {
		// タスク
		Callable<String> task = new Callable<String>() {
			public String call() throws IOException {
				try {
					return CallableWebClient.sendRequest();
				} catch(IOException e) {
					throw e;
				}
			}
		};

		// リクエスト
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Future<String>> futures = new ArrayList<Future<String>>();
		for(int i=0; i<NUM_THREADS; i++) {
			Future<String> future = executor.submit(task);
			futures.add(future);
		}

		// 結果受け取り
		for(int i=0; i<NUM_THREADS; i++) {
			Future<String> future = futures.get(i);
			try {
				System.out.println(future.get());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String sendRequest() throws IOException {
		URL url = new URL(TARGET_URL);
		URLConnection urlConnection = url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String buf;
		while((buf = br.readLine()) != null) {
			sb.append(buf);
		}
		br.close();
		return sb.toString();
	}

}
