package handson;

import java.util.ArrayList;
import java.util.List;

/**
 * �v�b�g�E�C�t�E�A�u�Z���g����������
 * �X���b�h�Z�[�t��
 * 1000�񃊃X�g�ɒǉ����Ă邩��A���X�g�̃T�C�Y��1000�ɂȂ�͂�
 */
public class List04_2 {
	private static final int NUM_LOOP = 1000;

	public static void main(String[] args) {
		// �X���b�h�Ԃŋ��L����I�u�W�F�N�g
		final ListHelper<Integer> sharedObject = new ListHelper<Integer>();

		Runnable r = new Runnable() {
			public void run() {
				for(int i=1; i<=NUM_LOOP; i++) {
					sharedObject.putIfAbsent(i);
				}
			}
		};

		// �X���b�h1�J�n
		Thread t1 = new Thread(r);
		t1.start();
		// �X���b�h2�J�n
		Thread t2 = new Thread(r);
		t2.start();
		
		try {
			// �����̃X���b�h���I���̂�҂����킹
			t1.join();
			t2.join();
		} catch(InterruptedException e) {
			// �������Ȃ�
		}

		// List�̐���\��(100�ɂȂ�͂�)
		System.out.println(sharedObject.list.size());
	}

	/**
	 * ���X�g�w���p�[
	 */
	private static class ListHelper<E> {
		private List<E> list = new ArrayList<E>();

		public boolean putIfAbsent(E x) {
			synchronized(list) {
				boolean absent = !list.contains(x);
				if(absent) {
					list.add(x);
				}
				return absent;
			}
		}
	}
}
