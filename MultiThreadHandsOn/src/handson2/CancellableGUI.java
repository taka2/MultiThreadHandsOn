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
 * ユーザがキャンセルをリクエスト可能なGUIのサンプル。
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

		// クリックボタンのイベントハンドラ
		buttonClick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				label.setText("processing...");
				
				Runnable r = new Runnable() {
					public void run() {
						// 何か重い処理
						try {
							Thread.sleep(3000);
						} catch(InterruptedException e) {
							// インタラプトされたら、タスクを途中で終了する
							return;
						}

						label.setText("clicked.");
					}
				};

				// スレッド1開始
				ExecutorService executor = Executors.newCachedThreadPool();
				buttonClickTask = executor.submit(r);
			}
		});
		// キャンセルボタンのイベントハンドラ
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

		// ×を押すと閉じる
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 100);
		frame.setVisible(true);
	}
}
