package handson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CancellableWebServer {
	private static final int THREAD_POOL_SIZE = 100;
	private static final String STOP_URI = "/stop";

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(8888);
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		while(true) {
			System.out.println("クライアントからの接続を待ち受けます。");
			final Socket client = ss.accept();
			System.out.println("クライアントから接続されました。");
			Callable<Boolean> task = new Callable<Boolean>() {
				public Boolean call() throws IOException {
					return Boolean.valueOf(handleRequest(client));
				}
			};
			Future<Boolean> future = executor.submit(task);
			if(!future.get()) {
				// webサーバ終了
				break;
			}
			System.out.println("クライアントを切断しました。");
		}

		executor.shutdown();
	}

	/**
	 * クライアントからのリクエストを処理する
	 * @param client クライアントのSocketオブジェクト
	 * @throws IOException IOエラー発生時
	 */
	private static boolean handleRequest(final Socket client) throws IOException {
		// 受付日時を取得
		Date acceptDate = new Date();
		// クライアントからのリクエスト内容を読み込み
		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String buf;
		String httpRequest = null;
		boolean isNormalCommand = true;
		while((buf = reader.readLine()) != null) {
			System.out.println("IN: " + buf);
			if(httpRequest == null) {
				httpRequest = buf;
				String httpRequestURI = httpRequest.split(" ")[1];
				if(STOP_URI.equals(httpRequestURI)) {
					isNormalCommand = false;
				}
			}
			if(buf.trim().length() == 0) {
				// 空行を読んだら読み込み終了
				break;
			}
		}

		// クライアントにレスポンスを返却
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/plain");
		writer.println("");
		writer.println(acceptDate);
		writer.flush();
		writer.close();
		
		client.close();
		
		return isNormalCommand;
	}
}
