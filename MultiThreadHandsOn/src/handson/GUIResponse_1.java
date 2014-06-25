package handson;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUIResponse_1 {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());

		final JLabel label = new JLabel("label");
		frame.add(label, BorderLayout.SOUTH);

		final JButton button = new JButton("click");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				label.setText("processing...");
				
				// âΩÇ©èdÇ¢èàóù
				try {
					Thread.sleep(3000);
				} catch(InterruptedException e) {
					// âΩÇ‡ÇµÇ»Ç¢
				}

				label.setText("clicked.");
			}
		});
		frame.add(button, BorderLayout.CENTER);

		// Å~ÇâüÇ∑Ç∆ï¬Ç∂ÇÈ
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(100, 100);
		frame.setVisible(true);
	}
}
