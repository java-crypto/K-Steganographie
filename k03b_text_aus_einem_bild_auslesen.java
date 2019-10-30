package net.bplaced.javacrypto.steganography;

/*
* Herkunft/Origin: http://javacrypto.bplaced.net/
* Programmierer/Programmer: Siddharth Satish
* Copyright/Copyright: nicht angegeben
* Copyright: not named.
* Lizenztext/Licence: not named
* getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
* getestet mit/tested with: Java Runtime Environment 11.0.1 x64
* Datum/Date (dd.mm.jjjj): 30.10.2019
* Projekt/Project: K03b Text aus einem Bild auslesen (mit GUI)
*                  K03b Unhide text from a picture (with GUI)
*
* Sicherheitshinweis/Security notice
* Die Programmroutinen dienen nur der Darstellung und haben keinen Anspruch auf eine korrekte Funktion, 
* insbesondere mit Blick auf die Sicherheit ! 
* Prüfen Sie die Sicherheit bevor das Programm in der echten Welt eingesetzt wird.
* The program routines just show the function but please be aware of the security part - 
* check yourself before using in the real world !
* 
* Das Projekt basiert auf dem nachfolgenden Github-Archiv des Autors:
* The project is based this Github-Archive of the Author:
* https://github.com/sidd-satish/image-steganography
*/

import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;

public class k03b_text_aus_einem_bild_auslesen extends JFrame implements ActionListener {
	JButton open = new JButton("Open"), decode = new JButton("Decode"), reset = new JButton("Reset");
	JTextArea message = new JTextArea(10, 3);
	BufferedImage image = null;
	JScrollPane imagePane = new JScrollPane();

	public k03b_text_aus_einem_bild_auslesen() {
		super("K03b Text aus einem Bild auslesen");
		assembleInterface();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		this.setVisible(true);
	}

	private void assembleInterface() {
		JPanel p = new JPanel(new FlowLayout());
		p.add(open);
		p.add(decode);
		p.add(reset);
		this.getContentPane().add(p, BorderLayout.NORTH);
		open.addActionListener(this);
		decode.addActionListener(this);
		reset.addActionListener(this);
		open.setMnemonic('O');
		decode.setMnemonic('D');
		reset.setMnemonic('R');

		p = new JPanel(new GridLayout(1, 1));
		p.add(new JScrollPane(message));
		message.setFont(new Font("Arial", Font.BOLD, 20));
		p.setBorder(BorderFactory.createTitledBorder("Decoded message"));
		message.setEditable(false);
		this.getContentPane().add(p, BorderLayout.SOUTH);

		imagePane.setBorder(BorderFactory.createTitledBorder("Steganographed Image"));
		this.getContentPane().add(imagePane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if (o == open)
			openImage();
		else if (o == decode)
			decodeMessage();
		else if (o == reset)
			resetInterface();
	}

	private java.io.File showFileDialog(boolean open) {
		JFileChooser fc = new JFileChooser("Open an image");
		javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() {
			public boolean accept(java.io.File f) {
				String name = f.getName().toLowerCase();
				return f.isDirectory() || name.endsWith(".png") || name.endsWith(".bmp");
			}

			public String getDescription() {
				return "Image (*.png, *.bmp)";
			}
		};
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(ff);

		java.io.File f = null;
		if (open && fc.showOpenDialog(this) == fc.APPROVE_OPTION)
			f = fc.getSelectedFile();
		else if (!open && fc.showSaveDialog(this) == fc.APPROVE_OPTION)
			f = fc.getSelectedFile();
		return f;
	}

	private void openImage() {
		java.io.File f = showFileDialog(true);
		try {
			image = ImageIO.read(f);
			JLabel l = new JLabel(new ImageIcon(image));
			imagePane.getViewport().add(l);
			this.validate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void decodeMessage() {
		int len = extractInteger(image, 0, 0);
		byte b[] = new byte[len];
		for (int i = 0; i < len; i++)
			b[i] = extractByte(image, i * 8 + 32, 0);
		message.setText(new String(b));
	}

	private int extractInteger(BufferedImage img, int start, int storageBit) {
		int maxX = img.getWidth(), maxY = img.getHeight(), startX = start / maxY, startY = start - startX * maxY,
				count = 0;
		int length = 0;
		for (int i = startX; i < maxX && count < 32; i++) {
			for (int j = startY; j < maxY && count < 32; j++) {
				int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
				length = setBitValue(length, count, bit);
				count++;
			}
		}
		return length;
	}

	private byte extractByte(BufferedImage img, int start, int storageBit) {
		int maxX = img.getWidth(), maxY = img.getHeight(), startX = start / maxY, startY = start - startX * maxY,
				count = 0;
		byte b = 0;
		for (int i = startX; i < maxX && count < 8; i++) {
			for (int j = startY; j < maxY && count < 8; j++) {
				int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
				b = (byte) setBitValue(b, count, bit);
				count++;
			}
		}
		return b;
	}

	private void resetInterface() {
		message.setText("");
		imagePane.getViewport().removeAll();
		image = null;
		this.validate();
	}

	private int getBitValue(int n, int location) {
		int v = n & (int) Math.round(Math.pow(2, location));
		return v == 0 ? 0 : 1;
	}

	private int setBitValue(int n, int location, int bit) {
		int toggle = (int) Math.pow(2, location), bv = getBitValue(n, location);
		if (bv == bit)
			return n;
		if (bv == 0 && bit == 1)
			n |= toggle;
		else if (bv == 1 && bit == 0)
			n ^= toggle;
		return n;
	}

	public static void main(String arg[]) {
		new k03b_text_aus_einem_bild_auslesen();
	}
}