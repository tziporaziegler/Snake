package snake;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class PauseDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public PauseDialog(SnakeFrame frame) {
		// TODO don't want to to be on top if entire game is not in focus/minimized
		setAlwaysOnTop(true);
		setSize(150, 50);
		// TODO location should be relative to where frame is on screen
		setLocationRelativeTo(null);
		setUndecorated(true);

		final JLabel label = new JLabel("PAUSED");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Traveling _Typewriter", Font.PLAIN, 40));
		add(label);

		// use keyAdapeter over implementing a keyListener because only want keyPressed
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				setVisible(false);
				frame.unpause();
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				frame.unpause();
			}
		});

	}
}