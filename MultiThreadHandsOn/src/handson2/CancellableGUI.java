package handson2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ���[�U���L�����Z�������N�G�X�g�\��GUI�̃T���v���B
 */
public class CancellableGUI {
	private static Future<?> buttonClickTask = null;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());

		final JLabel label = new JLabel("label");
		frame.add(label, BorderLayout.SOUTH);

		final JButton buttonClick = new JButton("click");
		final JButton buttonCancel = new JButton("cancel");

		// �N���b�N�{�^���̃C�x���g�n���h��
		buttonClick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				label.setText("processing...");
				
				Runnable r = new Runnable() {
					public void run() {
						// �����d������
						try {
							Thread.sleep(3000);
						} catch(InterruptedException e) {
							// �C���^���v�g���ꂽ��A�^�X�N��r���ŏI������
							return;
						}

						label.setText("clicked.");
					}
				};

				// �X���b�h1�J�n
				ExecutorService executor = Executors.newCachedThreadPool();
				buttonClickTask = executor.submit(r);
			}
		});
		// �L�����Z���{�^���̃C�x���g�n���h��
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				synchronized(buttonClickTask) {
					if(buttonClickTask != null && !buttonClickTask.isCancelled() && !buttonClickTask.isDone()) {
						buttonClickTask.cancel(true);
						label.setText("cancelled.");
					}
				}
			}
		});
		JPanel panel = new JPanel();
		panel.add(buttonClick);
		panel.add(buttonCancel);
		frame.add(panel, BorderLayout.NORTH);

		// �~�������ƕ���
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 100);
		frame.setVisible(true);
	}
}
