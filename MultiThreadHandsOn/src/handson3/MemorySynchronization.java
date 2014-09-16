package handson3;

/**
 * �������������̃R�X�g�����邽�߂̃T���v��
 */
public class MemorySynchronization {
	// �f�[�^��
	private static final int NUM_DATA = 200000;
	// �v�Z��
	private static final int CALC_AMOUNT = 100;

	public static void main(String[] args) {
		long startTime, endTime;

		// �������Ȃ�
		startTime = System.currentTimeMillis();
		for(int j=0; j<CALC_AMOUNT; j++) {
			long sum = 0;
			for(int i=0; i<NUM_DATA; i++) {
				sum += i;
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("�������Ȃ�: " + (endTime - startTime));
		
		// ���ۂɂ͓���������Ȃ���������
		startTime = System.currentTimeMillis();
		for(int j=0; j<CALC_AMOUNT; j++) {
			long sum = 0;
			for(int i=0; i<NUM_DATA; i++) {
				// ���ۂɂ͓���������Ȃ�
				synchronized(new Object()) {
					sum += i;
				}
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("���ۂɂ͓���������Ȃ���������: " + (endTime - startTime));

		// ����������
		Object lock = new Object();
		startTime = System.currentTimeMillis();
		for(int j=0; j<CALC_AMOUNT; j++) {
			long sum = 0;
			for(int i=0; i<NUM_DATA; i++) {
				// �����������
				//synchronized(MemorySynchronization.class) {
				synchronized(lock) {
					sum += i;
				}
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("����������: " + (endTime - startTime));

	}
}
