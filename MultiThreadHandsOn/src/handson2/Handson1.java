package handson2;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ハンズオン１の回答例
 */
public class Handson1 {
	private static class CallableTask implements Callable<File[]> {
		private String path;
		public CallableTask(String path) {
			this.path = path;
		}
		public File[] call() throws IOException {
			return new File(path).listFiles();
		}
	};

	public static void main(String[] args) throws Exception {

		// リクエスト
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<File[]> future = executor.submit(new CallableTask("src/handson"));
		Future<File[]> future2 = executor.submit(new CallableTask("src/handson2"));
		File[] files = (File[])future.get();
		for(File file : files) {
			System.out.println(file);
		}
		File[] files2 = (File[])future2.get();
		for(File file : files2) {
			System.out.println(file);
		}
		executor.shutdown();
	}	
}
