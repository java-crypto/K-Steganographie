package net.bplaced.javacrypto.steganography.k04;

// Das Programm geh�rt zu k04_steganography.java
// the program belongs to k04_steganography.java

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class k04_MyDialog extends JDialog implements ActionListener {

	JFrame frame;
	private JTextArea textArea;
	private JButton button11;

	k04_MyDialog(Frame frame, String title) {
		super(frame, title, true);
		textArea = new JTextArea();
		button11 = new JButton("OK");
		button11.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane);
		getContentPane().add(BorderLayout.SOUTH, button11);

		setSize(400, 400);
		setLocationRelativeTo(frame);
	}

	public String getText() throws NullPointerException {
		if (textArea.getText() == "") {
			Component veiw = null;
			JOptionPane.showMessageDialog(veiw, "Please Enter Your Message", "Error!", JOptionPane.ERROR_MESSAGE);
			throw new NullPointerException("Please enter message");
		} else
			return textArea.getText();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button11) {
			setVisible(false);
		}
	}
}
