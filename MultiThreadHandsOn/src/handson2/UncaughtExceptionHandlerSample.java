package handson2;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * UncaughtExceptionHandler�̓��������T���v��
 */
public class UncaughtExceptionHandlerSample {
	public static void main(String[] args) {
		// UncaughtExceptionHandler���`
		UncaughtExceptionHandler ueh = new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				// �ʏ�A�W���G���[�o�͂ɏo��Ƃ���A�W���o�͂ɕ\������悤�ύX
				e.printStackTrace(System.out);
			}
		};

		// �J�����g�X���b�h��Handler���Z�b�g
		Thread.setDefaultUncaughtExceptionHandler(ueh);
		
		// �����^�C����O���i�Ӑ}�I�Ɂj�X���[
		throw new RuntimeException("Runtime Exception!");
	}
}
