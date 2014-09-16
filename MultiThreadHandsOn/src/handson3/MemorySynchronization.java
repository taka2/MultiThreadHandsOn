package handson3;

/**
 * メモリ同期化のコストを見るためのサンプル
 */
public class MemorySynchronization {
	// データ数
	private static final int NUM_DATA = 200000;
	// 計算量
	private static final int CALC_AMOUNT = 100;

	public static void main(String[] args) {
		long startTime, endTime;

		// 同期化なし
		startTime = System.currentTimeMillis();
		for(int j=0; j<CALC_AMOUNT; j++) {
			long sum = 0;
			for(int i=0; i<NUM_DATA; i++) {
				sum += i;
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("同期化なし: " + (endTime - startTime));
		
		// 実際には同期化されない同期処理
		startTime = System.currentTimeMillis();
		for(int j=0; j<CALC_AMOUNT; j++) {
			long sum = 0;
			for(int i=0; i<NUM_DATA; i++) {
				// 実際には同期化されない
				synchronized(new Object()) {
					sum += i;
				}
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("実際には同期化されない同期処理: " + (endTime - startTime));

		// 同期化あり
		Object lock = new Object();
		startTime = System.currentTimeMillis();
		for(int j=0; j<CALC_AMOUNT; j++) {
			long sum = 0;
			for(int i=0; i<NUM_DATA; i++) {
				// 同期化される
				//synchronized(MemorySynchronization.class) {
				synchronized(lock) {
					sum += i;
				}
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("同期化あり: " + (endTime - startTime));

	}
}
