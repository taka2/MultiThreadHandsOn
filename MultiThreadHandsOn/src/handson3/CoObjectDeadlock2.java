package handson3;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���͂�������̃I�u�W�F�N�g�Ԃ̃��b�N���f�b�h���b�N�̃T���v��
 * �I�[�v���R�[�����g���ċ��͂����������̃I�u�W�F�N�g�Ԃ̃f�b�h���b�N��h���T���v��
 */
public class CoObjectDeadlock2 {
	// 1��ł͍Č����Ȃ��̂ŁA���[�v������
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
	 * �^�N�V�[��\���N���X
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
			// Taxi�̃��b�N���擾
			synchronized(this) {
				this.location = location;
				reachedDestination = location.equals(destination);
			}
			// Taxi�̃��b�N�����
			if(reachedDestination) {
				// ���̃��b�N��ۗL���Ȃ��Ń��\�b�h���Ăяo�����I�[�v���R�[��
				// Dispatcher�̃��b�N���擾
				dispatcher.notifyAvailable(this);
				// Dispatcher�̃��b�N�����
			}
		}
	}

	/**
	 * �z�Ԃ�\���N���X
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
			// Dispatcher�̃��b�N���擾
			synchronized(this) {
				copy = new HashSet<Taxi>(taxis);
			}
			// Dispatcher�̃��b�N�����
			Image image = new Image();

			for(Taxi t : copy) {
				// ���̃��b�N��ۗL���Ȃ��Ń��\�b�h���Ăяo�����I�[�v���R�[��
				// Taxi�iCopy�j�̃��b�N���擾
				image.drawMarker(t.getLocation());
				// Taxi�iCopy�j�̃��b�N�����
			}

			return image;
		}
	}
	
	static class Image {
		public void drawMarker(Point location) {
			// �������Ȃ�
		}
	}
}
