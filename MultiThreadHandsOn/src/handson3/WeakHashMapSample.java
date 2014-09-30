package handson3;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * WeakHashMap�̓���𒲂ׂ�T���v��
 */
public class WeakHashMapSample {

	public static void main(String[] args) {
		// ���ʂ�HashMap
		Map<String, Integer> normalMap = new HashMap<String, Integer>();

		// �l��ǉ��i"hoge"=1�j
		String key = new String("hoge");
		normalMap.put(key, 1);

		// �l��\��
		System.out.println("���ʂ�HashMap");
		for(Map.Entry<String, Integer> entry : normalMap.entrySet()) {
			System.out.println(entry);
		}
		
		// �L�[�l"hoge"����Q�Ƃɂ���
		key = new String("moge");
		// �K�x�[�W�R���N�^�����s
		System.gc();
		
		// �l��\��
		System.out.println("���ʂ�HashMap");
		for(Map.Entry<String, Integer> entry : normalMap.entrySet()) {
			System.out.println(entry);
		}

		// WeakHashMap
		Map<String, Integer> weakMap = new WeakHashMap<String, Integer>();

		// ���ꂼ��l��ǉ��i"hoga"=2�j
		String key2 = new String("hoga");
		weakMap.put(key2, 2);

		// �l��\��
		System.out.println("WeakHashMap");
		for(Map.Entry<String, Integer> entry : weakMap.entrySet()) {
			System.out.println(entry);
		}
		
		// �L�[�l"hoga"����Q�Ƃɂ���
		key2 = new String("moga");
		// �K�x�[�W�R���N�^�����s
		System.gc();
		
		// �l��\��
		System.out.println("WeakHashMap");
		for(Map.Entry<String, Integer> entry : weakMap.entrySet()) {
			System.out.println(entry);
		}
	}
}
