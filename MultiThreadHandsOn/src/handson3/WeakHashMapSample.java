package handson3;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * WeakHashMapの動作を調べるサンプル
 */
public class WeakHashMapSample {

	public static void main(String[] args) {
		// 普通のHashMap
		Map<String, Integer> normalMap = new HashMap<String, Integer>();

		// 値を追加（"hoge"=1）
		String key = new String("hoge");
		normalMap.put(key, 1);

		// 値を表示
		System.out.println("普通のHashMap");
		for(Map.Entry<String, Integer> entry : normalMap.entrySet()) {
			System.out.println(entry);
		}
		
		// キー値"hoge"を弱参照にする
		key = new String("moge");
		// ガベージコレクタを実行
		System.gc();
		
		// 値を表示
		System.out.println("普通のHashMap");
		for(Map.Entry<String, Integer> entry : normalMap.entrySet()) {
			System.out.println(entry);
		}

		// WeakHashMap
		Map<String, Integer> weakMap = new WeakHashMap<String, Integer>();

		// それぞれ値を追加（"hoga"=2）
		String key2 = new String("hoga");
		weakMap.put(key2, 2);

		// 値を表示
		System.out.println("WeakHashMap");
		for(Map.Entry<String, Integer> entry : weakMap.entrySet()) {
			System.out.println(entry);
		}
		
		// キー値"hoga"を弱参照にする
		key2 = new String("moga");
		// ガベージコレクタを実行
		System.gc();
		
		// 値を表示
		System.out.println("WeakHashMap");
		for(Map.Entry<String, Integer> entry : weakMap.entrySet()) {
			System.out.println(entry);
		}
	}
}
