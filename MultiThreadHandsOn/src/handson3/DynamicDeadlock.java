package handson3;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 動的なロック順デッドロックのサンプル
 */
public class DynamicDeadlock {
	// 1回では再現しないので、ループさせる
	private static final int LOOP_COUNT = 1000;

	public static void main(String[] args) {
		final Account accountA = new Account();
		final Account accountB = new Account();
		
		// 口座Aから口座Bに10,000円送金するタスク
		Runnable transferAtoBTask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					new Deal().transferMoney(accountA, accountB, new BigDecimal(10000));
				}
			}
		};

		// 口座Bから口座Aに20,000円送金するタスク
		Runnable transferBtoATask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					new Deal().transferMoney(accountB, accountA, new BigDecimal(20000));
				}
			}
		};
		
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(transferAtoBTask);
		service.submit(transferBtoATask);

		service.shutdown();
	}

	/**
	 * 口座を表すクラス
	 */
	private static class Account {
		public void debit(BigDecimal amount) {
			// 実装しない
		}
		public void credit(BigDecimal amount) {
			// 実装しない
		}
	}

	/**
	 * 取引を表すクラス
	 */
	private static class Deal {
		/**
		 * fromAccountからtoAccountへ指定したamount送金する
		 * @param fromAccount
		 * @param toAccount
		 * @param amount
		 */
		public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
			synchronized(fromAccount) {
				synchronized(toAccount) {
					fromAccount.debit(amount);
					toAccount.credit(amount);
				}
			}
		}
	}
}
