package handson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 複数スレッドでアクセスする簡易webクライアント
 */
public class WebClient {
	private static final int NUM_THREADS = 100;
	private static final String TARGET_URL = "http://localhost:8888";

	public static void main(String[] args) throws IOException {
		for(int i=0; i<NUM_THREADS; i++) {
			new Thread() {
				public void run() {
					try {
						WebClient.sendRequest();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
	private static void sendRequest() throws IOException {
		URL url = new URL(TARGET_URL);
		URLConnection urlConnection = url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String buf;
		while((buf = br.readLine()) != null) {
			System.out.println(buf);
		}
		br.close();		
	}

}
