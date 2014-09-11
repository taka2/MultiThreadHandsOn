package handson3;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 協力しあう二つのオブジェクト間のロック順デッドロックのサンプル
 */
public class CoObjectDeadlock {
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
		
		public synchronized void setLocation(Point location) {
			this.location = location;
			if(location.equals(destination)) {
				dispatcher.notifyAvailable(this);
			}
		}
	}
	
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
		
		public synchronized Image getImage() {
			Image image = new Image();
			for(Taxi t : taxis) {
				image.drawMarker(t.getLocation());
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
