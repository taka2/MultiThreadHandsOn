package handson3;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 協力しあう二つのオブジェクト間のロック順デッドロックのサンプル
 * オープンコールを使って協力しあう複数のオブジェクト間のデッドロックを防ぐサンプル
 */
public class CoObjectDeadlock2 {
	// 1回では再現しないので、ループさせる
	private static final int LOOP_COUNT = 1000;

	public static void main(String[] args) {
		final Dispatcher d = new Dispatcher();
		final Taxi t = new Taxi(d, new Point(1, 1)); 
		
		Runnable getLocationTask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					t.setLocation(new Point(1, 1));
				}
			}
		};

		Runnable getImageTask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					d.getImage();
				}
			}
		};

		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(getLocationTask);
		service.submit(getImageTask);

		service.shutdown();
	}

	/**
	 * タクシーを表すクラス
	 */
	static class Taxi {
		private Point location, destination;
		private final Dispatcher dispatcher;
		
		public Taxi(Dispatcher dispatcher, Point destination) {
			this.dispatcher = dispatcher;
			this.destination = destination;
			dispatcher.taxis.add(this);
		}
		
		public synchronized Point getLocation() {
			return location;
		}
		
		public void setLocation(Point location) {
			boolean reachedDestination;
			// Taxiのロックを取得
			synchronized(this) {
				this.location = location;
				reachedDestination = location.equals(destination);
			}
			// Taxiのロックを解放
			if(reachedDestination) {
				// 他のロックを保有しないでメソッドを呼び出す＝オープンコール
				// Dispatcherのロックを取得
				dispatcher.notifyAvailable(this);
				// Dispatcherのロックを解放
			}
		}
	}

	/**
	 * 配車を表すクラス
	 */
	static class Dispatcher {
		private final Set<Taxi> taxis;
		private final Set<Taxi> availableTaxis;
		
		public Dispatcher() {
			taxis = new HashSet<Taxi>();
			availableTaxis = new HashSet<Taxi>();
		}
		
		public synchronized void notifyAvailable(Taxi taxi) {
			availableTaxis.add(taxi);
		}
		
		public Image getImage() {
			Set<Taxi> copy;
			// Dispatcherのロックを取得
			synchronized(this) {
				copy = new HashSet<Taxi>(taxis);
			}
			// Dispatcherのロックを解放
			Image image = new Image();

			for(Taxi t : copy) {
				// 他のロックを保有しないでメソッドを呼び出す＝オープンコール
				// Taxi（Copy）のロックを取得
				image.drawMarker(t.getLocation());
				// Taxi（Copy）のロックを解放
			}

			return image;
		}
	}
	
	static class Image {
		public void drawMarker(Point location) {
			// 実装しない
		}
	}
}
