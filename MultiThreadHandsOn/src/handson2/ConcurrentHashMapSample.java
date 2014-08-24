package handson2;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap���g���āA
 * iterate�Ɠ����ɏ����������s�����Ƃ��ɁA���f���邩�ǂ����̃e�X�g
 */
public class ConcurrentHashMapSample {
	private static final int NUM_DATA = 10000;
	public static void main(String args[]) {
		final Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
		
		new Thread(new Runnable() {
			public void run() {
				for(int i=0; i<NUM_DATA; i++) {
					map.put(i, i);
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				System.out.println("map.size(iterator�擾�O) = " + map.size());
				Iterator<Integer> ite = map.keySet().iterator();
				System.out.println("map.size(iterator�擾��) = " + map.size());
				int count = 0;
				while(ite.hasNext()) {
					ite.next();
					count++;
				}
				System.out.println("iterator���[�v�J�E���g = " + count);
			}
		}).start();
	}
}
