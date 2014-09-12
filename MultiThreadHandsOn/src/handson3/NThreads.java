package handson3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * スレッド数とスループットの関係を調べるプログラム
 */
public class NThreads {
	// タスク数（負荷の大きさ）
	private static final int LOOP_COUNT = 100000;
	
	public static void main(String[] args) {
		// ファイルを読み込んで行数を返すコーラブルタスク
		Callable<Integer> fileReadTask = new Callable<Integer>() {
			public Integer call() throws IOException {
				BufferedReader br = new BufferedReader(new FileReader("src/handson3/NThreads.java"));
				int numLines = 0;
				while(br.readLine() != null) {
					numLines++;
				}
				br.close();
				return numLines;
			}
		};
		
		// スレッド数を変えながらタスクを実行する（タスク数は定数で設定）
		int[] NUM_THREADS = {1, 2, 3, 4, 5, 10, 20, 50, 100};
		for(int numThreads : NUM_THREADS) {
			long executionTime = executeTask(fileReadTask, numThreads);
			System.out.printf("numThreads = %3d, executionTime = %5d[ms]\n", numThreads, executionTime);
		}
	}
	
	/**
	 * 指定したタスクを指定したスレッド数で実行する
	 * @param task タスク
	 * @param numThreads スレッド数
	 * @return かかった時間[ms]
	 */
	private static long executeTask(Callable<Integer> task, int numThreads) {
		// 開始時刻
		long startTime = System.currentTimeMillis();

		List<Future<Integer>> taskResults = new ArrayList<Future<Integer>>();
		ExecutorService service = Executors.newFixedThreadPool(numThreads);
		// タスクの実行を依頼
		for(int i=0; i<LOOP_COUNT; i++) {
			taskResults.add(service.submit(task));
		}
		
		for(Future<Integer> taskResult : taskResults) {
			try {
				// タスクの実行結果を取得
				taskResult.get();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		service.shutdown();

		// 終了時刻
		long endTime = System.currentTimeMillis();
		return (endTime - startTime);
	}
}
