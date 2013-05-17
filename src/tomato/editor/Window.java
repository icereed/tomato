package tomato.editor;

import java.awt.BorderLayout;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window {
	private JFrame frame;
	private JPanel blocks;

	public Window() {
		frame = new JFrame();

		blocks = new JPanel();

		frame.getContentPane().add(BorderLayout.EAST, blocks);
		
		blocks.add(new JColorChooser());
		
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
