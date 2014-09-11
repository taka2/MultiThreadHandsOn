package handson3;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���I�ȃ��b�N���f�b�h���b�N
 * ���b�N�̏�����U�����ăf�b�h���b�N��h���T���v��
 */
public class DynamicDeadlock2 {
	// 1��ł͍Č����Ȃ��̂ŁA���[�v������
	private static final int LOOP_COUNT = 1000;

	public static void main(String[] args) {
		final Account accountA = new Account();
		final Account accountB = new Account();
		
		// ����A�������B��10,000�~��������^�X�N
		Runnable transferAtoBTask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					new Deal().transferMoneyNoDeadlock(accountA, accountB, new BigDecimal(10000));
				}
			}
		};

		// ����B�������A��20,000�~��������^�X�N
		Runnable transferBtoATask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					new Deal().transferMoneyNoDeadlock(accountB, accountA, new BigDecimal(20000));
				}
			}
		};
		
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(transferAtoBTask);
		service.submit(transferBtoATask);

		service.shutdown();
	}

	/**
	 * ������\���N���X
	 */
	private static class Account {
		public void debit(BigDecimal amount) {
			// �������Ȃ�
		}
		public void credit(BigDecimal amount) {
			// �������Ȃ�
		}
	}

	/**
	 * �����\���N���X
	 */
	private static class Deal {
		private Object tieLock = new Object();

		/**
		 * fromAccount����toAccount�֎w�肵��amount��������
		 * @param fromAccount
		 * @param toAccount
		 * @param amount
		 */
		public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
			fromAccount.debit(amount);
			toAccount.credit(amount);		
		}

		public void transferMoneyNoDeadlock(Account fromAccount, Account toAccount, BigDecimal amount) {
			int fromHash = System.identityHashCode(fromAccount);
			int toHash = System.identityHashCode(toAccount);
			if(fromHash < toHash) {
				synchronized(fromAccount) {
					synchronized(toAccount) {
						transferMoney(fromAccount, toAccount, amount);
					}
				}
			} else if(toHash < fromHash) {
				synchronized(toAccount) {
					synchronized(fromAccount) {
						transferMoney(fromAccount, toAccount, amount);
					}
				}
			} else {
				synchronized(tieLock) {
					synchronized(fromAccount) {
						synchronized(toAccount) {
							transferMoney(fromAccount, toAccount, amount);
						}
					}
				}
			}
		}
	}
}
