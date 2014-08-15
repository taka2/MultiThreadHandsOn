package handson2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolSample {
	private static final int THREAD_POOL_SIZE = 100;

	public static void main(String[] args) throws Exception {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
		executorService.scheduleAtFixedRate(new MyTimerTask(), 0, 1, TimeUnit.SECONDS);
	}
}
