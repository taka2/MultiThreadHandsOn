package handson3;

/**
 * ���b�N�����̃T���v��
 * �Ɨ�������Ԃ����I�u�W�F�N�g�ɑ΂��āA�C���X�^���X�P�ʂ̃��b�N�ł͂Ȃ��A
 * ���ꂼ��̏�Ԃ��ƂɃ��b�N��������悤�ɂ���B
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
	 * CellPhone�I�u�W�F�N�g�Ƀ��b�N�����������
	 */
	private static class CellPhone {
		// �d�g���x
		private int denpa;
		// �o�b�e���[
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
	 * �d�g���x�A�o�b�e���[���ꂼ��Ƀ��b�N�����������
	 */
	private static class CellPhone6 {
		// �d�g���x
		private Integer denpa;
		// �o�b�e���[
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
