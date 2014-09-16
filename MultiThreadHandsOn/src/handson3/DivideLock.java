package handson3;

/**
 * ロック分割のサンプル
 * 独立した状態を持つオブジェクトに対して、インスタンス単位のロックではなく、
 * それぞれの状態ごとにロックをかけるようにする。
 */
public class DivideLock {
	public static void main(String[] args) {
		CellPhone cellPhone = new CellPhone();
		cellPhone.setDenpa(5);
		cellPhone.setBattery(cellPhone.getBattery() - 10);
		System.out.println(cellPhone);
		
		CellPhone6 cellPhone6 = new CellPhone6();
		cellPhone6.setDenpa(5);
		cellPhone6.setBattery(cellPhone6.getBattery() - 5);
		System.out.println(cellPhone6);
	}

	/**
	 * CellPhoneオブジェクトにロックをかける実装
	 */
	private static class CellPhone {
		// 電波強度
		private int denpa;
		// バッテリー
		private int battery;

		public CellPhone() {
			this.denpa = 0;
			this.battery = 100;
		}

		public synchronized int getDenpa() {
			return denpa;
		}
		public synchronized void setDenpa(int denpa) {
			this.denpa = denpa;
		}
		public synchronized int getBattery() {
			return battery;
		}
		public synchronized void setBattery(int battery) {
			this.battery = battery;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("denpa = " + getDenpa() + System.getProperty("line.separator"));
			sb.append("battery = " + getBattery());
			return sb.toString();
		}
	}

	/**
	 * 電波強度、バッテリーそれぞれにロックをかける実装
	 */
	private static class CellPhone6 {
		// 電波強度
		private Integer denpa;
		// バッテリー
		private Integer battery;

		public CellPhone6() {
			this.denpa = 0;
			this.battery = 100;
		}

		public int getDenpa() {
			synchronized(this.denpa) {
				return denpa;
			}
		}
		public synchronized void setDenpa(Integer denpa) {
			synchronized(this.denpa) {
				this.denpa = denpa;
			}
		}
		public int getBattery() {
			synchronized(this.battery) { 
				return battery;
			}
		}
		public void setBattery(Integer battery) {
			synchronized(this.battery) {
				this.battery = battery;
			}
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("denpa = " + getDenpa() + System.getProperty("line.separator"));
			sb.append("battery = " + getBattery());
			return sb.toString();
		}
	}
}
