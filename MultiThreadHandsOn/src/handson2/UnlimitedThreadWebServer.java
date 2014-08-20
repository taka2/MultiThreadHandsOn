package handson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * クライアントからのリクエストを受け付けるたびにスレッドを生成するwebサーバの例
 */
public class UnlimitedThreadWebServer {
	private static final int MEMORY_USAGE = 1000000;
	private static final int PROCESS_TIME = 3000;

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(8888);
		while(true) {
			System.out.println("クライアントからの接続を待ち受けます。");
			final Socket client = ss.accept();
			System.out.println("クライアントから接続されました。");
			new Thread() {
				public void run() {
					try {
						handleRequest(client);
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
			System.out.println("クライアントを切断しました。");
		}
	}

	/**
	 * クライアントからのリクエストを処理する
	 * @param client クライアントのSocketオブジェクト
	 * @throws IOException IOエラー発生時
	 */
	private static void handleRequest(final Socket client) throws IOException {
		// 受付日時を取得
		Date acceptDate = new Date();
		// クライアントからのリクエスト内容を読み込み
		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String buf;
		while((buf = reader.readLine()) != null) {
			System.out.println("IN: " + buf);
			if(buf.trim().length() == 0) {
				// 空行を読んだら読み込み終了
				break;
			}
		}

		// 何か重い処理
		try {
			int[] data = new int[MEMORY_USAGE];
			Thread.sleep(PROCESS_TIME);
		} catch(InterruptedException e) {
			// 何もしない
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
	}
}
