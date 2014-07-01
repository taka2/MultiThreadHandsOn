package handson;

/**
 * 変数スコープについて説明するサンプル
 */
public class VariableScope {

	public static void main(String[] args) {
		new MyThread(0).start();
		new MyThread(1).start();
	}

	private static class MyThread extends Thread {
		private static int classValue;
		private int instanceValue;
		private int offset;
		
		public MyThread(int offset) {
			this.offset = offset;
		}
		public void run() {
			int localValue = 0;

			System.out.println("localValue = " + localValue);
			System.out.println("instanceValue = " + this.instanceValue);
			System.out.println("classValue = " + MyThread.classValue);

			localValue = 1 + offset;
			this.instanceValue = 2 + offset;
			MyThread.classValue = 3 + offset;

			System.out.println("localValue = " + localValue);
			System.out.println("instanceValue = " + this.instanceValue);
			System.out.println("classValue = " + MyThread.classValue);
		}
	}
}
