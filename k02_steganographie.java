package net.bplaced.javacrypto.steganography;

/*
* Herkunft/Origin: http://javacrypto.bplaced.net/
* Programmierer/Programmer: Ricardo Sanchez
* Copyright/Copyright: nicht angegeben
* Copyright: not named.
* Lizenztext/Licence: not named
* getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
* getestet mit/tested with: Java Runtime Environment 11.0.1 x64
* Datum/Date (dd.mm.jjjj): 29.10.2019
* Projekt/Project: K02 Text in einem Bild verstecken
*                  K02 Hide text in a picture
*
* Sicherheitshinweis/Security notice
* Die Programmroutinen dienen nur der Darstellung und haben keinen Anspruch auf eine korrekte Funktion, 
* insbesondere mit Blick auf die Sicherheit ! 
* Pr�fen Sie die Sicherheit bevor das Programm in der echten Welt eingesetzt wird.
* The program routines just show the function but please be aware of the security part - 
* check yourself before using in the real world !
* 
* Das Projekt basiert auf dem nachfolgenden Github-Archiv des Autors:
* The project is based this Github-Archive of the Author:
* https://github.com/RicardoSanchezA/Steganography
* 
* Ich habe das Programm eingedeutscht / I translated the program to German
* 
*/

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.StringBuilder;
import java.util.Scanner;

/**
 * The Steganography program conceals text messages within an image. The user
 * needs to provide a path to an image that will be used to hide/read a message.
 * If the user doesn't have any images, then (s)he should enter an empty line
 * whenever the program asks for the path to the image and the default image
 * will be used. The original image will not be overwritten, instead the program
 * will use a different filename to save a copy that contains the hidden
 * message.
 * 
 * The least significant bit of each component of each pixel (i.e. Red, Green,
 * Blue, Alpha(transparency)) of the image is overwritten with the message that
 * the user provides. Thus, we use 4 bits per pixel, and our algorithm uses 2
 * pixels (i.e. 8 bits) to store 1 character of the concealed message.
 *
 * @author Ricardo Sanchez
 * @version 1.0
 * @since 2018-02-12
 */
class ImageHelper {
	public static boolean save_image(BufferedImage img, String path) {
		try {
			File f = new File(path);
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			System.out.println(" >> Der angegebene Pfad hat keine g�ltige Bilddatei");
			return false;
		}
		return true;
	}

	public static BufferedImage open_image(String path) {
		BufferedImage img = null;
		try {
			File f = new File(path);
			img = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println(" >> Der angegebene Pfad hat keine g�ltige Bilddatei");
		}
		return img;
	}

	public static BufferedImage convert_to_aRGB(BufferedImage img, String path) {
		BufferedImage _img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D _imgG = _img.createGraphics();
		_imgG.drawImage(img, 0, 0, null);
		save_image(_img, path);
		System.out.println(" >> Das Bild wurde in ein aRGB (Transparenz)-Bild umgewandelt");
		return open_image(path);
	}
}

public class k02_steganographie {
	/* Variables */
	private final static String cwd = System.getProperty("user.dir");
	private final static String null_char = "11111111";
	private final static char null_char_value = (char) Integer.parseInt(null_char, 2);
	private static String image_path = "";
	private static int first_run = 0;

	/* Methods */
	private static BufferedImage get_image(Scanner sc) {
		BufferedImage img = null;
		String path = "";
		if (first_run < 2) {
			System.out.println("(Sie k�nnen eine leere Zeile best�tigen f�r das Default Bild)");
			first_run += 1;
		}
		while (img == null) {
			System.out.print("Geben Sie den Pfad zu einer Bilddatei ein: ");
			String input = sc.nextLine();
			if (input.length() > 0) {
				if (!input.contains(".png")) {
					System.out.println(" >> Der Pfad muss zu einem g�ltigen PNG-Bild f�hren!");
					continue;
				}
				path = input;
			} else {
				path = image_path;
			}
			img = ImageHelper.open_image(path);
		}
		image_path = path;

		/*
		 * If the given image doesn't support transparency (i.e. Alpha), then convert
		 * the image to a Color Model that supports Alpha.
		 */
		if (img.getColorModel().hasAlpha() == false) {
			img = ImageHelper.convert_to_aRGB(img, image_path);
		}

		return img;
	}

	private static void read_message(Scanner sc) {
		image_path = cwd + "/images/default(hidden message).png";
		BufferedImage img = get_image(sc);
		boolean contains_hidden_message = true;

		/*
		 * Make the width a factor of 2 since we are going to be reading 2 adjacent
		 * pixels at a time (e.g. if an image has an odd width, say 219 pixels, we would
		 * treat such image as if it had a width of 218 pixels).
		 */
		int width = img.getWidth();
		if (width % 2 == 1) {
			width -= 1;
		}

		StringBuilder hidden_message = new StringBuilder();
		for (int y = 0; y < img.getHeight() && contains_hidden_message; y += 1) {
			for (int x = 0; x < width && contains_hidden_message; x += 2) {
				char c = read_from_pixels(x, y, img);
				/* Check if current character is a message-terminating character. */
				if (c == null_char_value) {
					y = img.getHeight();
					break;
				}
				/* Check if the current character is valid (typeable ASCII). */
				else if (!(c >= 32 && c <= 126)) {
					contains_hidden_message = false;
				}
				hidden_message.append(c);
			}
		}

		if (contains_hidden_message) {
			System.out.println("\ngenutztes Bild: " + image_path);
			System.out.println("Geheime Nachricht: \"" + hidden_message.toString() + "\"\n");
		} else {
			System.out.println("Das Bild enth�lt keine geheime Nachricht (mit diesem Programm erzeugt)\n");
		}

	}

	private static void hide_message(Scanner sc) {
		image_path = cwd + "/images/default.png";
		BufferedImage img = get_image(sc);

		String message;
		System.out.print("Geben Sie die geheime Nachricht ein: ");
		message = sc.nextLine();

		/*
		 * Make the width a factor of 2 since we are going to be reading 2 adjacent
		 * pixels at a time (e.g. if an image has an odd width, say 219 pixels, we would
		 * treat such image as if it had a width of 218 pixels).
		 */
		int width = img.getWidth();
		if (width % 2 == 1) {
			width -= 1;
		}

		/* Hide the message in the image starting from the (0,0) coordinate. */
		boolean wrote_full_message = false;
		int x, y, i;
		x = y = i = 0;
		for (y = 0; y < img.getHeight() && i < message.length(); y += 1) {
			for (x = 0; x < width && i < message.length(); x += 2, i += 1) {
				int character = message.charAt(i);
				String character_bits = Integer.toBinaryString(character);
				// Writes the character to pixels (x,y) and (x+1,y).
				write_to_pixels(x, y, img, character_bits);
			}
		}
		if (i == message.length()) {
			// Write message-terminating character.
			if (x < width) {
				write_to_pixels(x, y - 1, img, null_char);
			} else if (y < img.getHeight()) {
				write_to_pixels(0, y, img, null_char);
			}
			wrote_full_message = true;
		}

		/* Write the image with the hidden message as a file */
		image_path = image_path.replaceFirst(".png", "_k02.png");
		boolean success = ImageHelper.save_image(img, image_path);

		/* Print Success/Failure messages */
		if (success) {
			if (wrote_full_message) {
				System.out.println("\nDie Nachricht wurde im Bild versteckt!");
			} else {
				System.out.println("\nDas Bild enth�lt nicht genug Platz f�r die gesamte Nachricht."
						+ " Dieser Teil wurde versteckt: \"" + message.substring(0, i) + "\".");
			}
			System.out.println("Image location: " + image_path + "\n");
		} else {
			System.out.println("Something went wrong...\n");
		}
	}

	private static void write_to_pixels(int x, int y, BufferedImage img, String character_bits) {
		/*
		 * In this function we will write a single character from the message in the
		 * (x,y) and (x+1,y) pixels of the image. The binary representation of such
		 * character is stored in CHARACTER_BITS. We will overwrite the LSB of the
		 * Alpha, Red, Green, and Blue components of each pixel to store such
		 * information. Hence, we will use 8-bits per character to store the message in
		 * the image.
		 */

		/*
		 * We will add leading zeroes to CHARACTER_BITS to ensure that its size is 8
		 * characters long. Each character in CHARACTER_BITS represents a bit, so all
		 * values are 1 or 0.
		 */
		while (character_bits.length() < 8) {
			character_bits = "0" + character_bits;
		}

		for (int i = 0; i <= 1; i += 1) {
			/* Extract the corresponding pixel/color from the image. */
			Color pixel = new Color(img.getRGB(x + i, y), true);

			/*
			 * In this array (aRGB) we will store the 4 components of a pixel (i.e. the
			 * [A]lpha, [R]ed, [G]reen, and [B]lue values).
			 */
			int[] aRGB = new int[4];

			int pixel_aRGB = pixel.getRGB();
			int shift = 24;
			/*
			 * In the next 'for' loop we will extract the LSB of each component and
			 * overwrite it with the data from the message that we want to hide.
			 */
			for (int j = 0; j < 4; j += 1, shift -= 8) {
				/* Extract LSB from pixel's aRGB value. */
				aRGB[j] = ((pixel_aRGB >> shift) & 0xfe);
				/* Overwrite LSB with character data from the message. */
				aRGB[j] |= (character_bits.charAt(0) - '0');
				/* Remove first character from CHARACTER_BITS. */
				character_bits = character_bits.substring(1, character_bits.length());
			}
			/* Create a new color/pixel with the modified aRGB components. */
			Color new_pixel = new Color(aRGB[1], aRGB[2], aRGB[3], aRGB[0]);
			/* Write the modified pixel back to the image. */
			img.setRGB(x + i, y, new_pixel.getRGB());
		}
	}

	private static char read_from_pixels(int x, int y, BufferedImage img) {
		/*
		 * We will store the binary representation of the character that we will read
		 * from the pixels (x,y) and (x+1,y) in CHARACTER_BINARY.
		 */
		StringBuilder character_binary = new StringBuilder();

		for (int i = 0; i <= 1; i += 1) {
			Color pixel = new Color(img.getRGB(x + i, y), true);
			int pixel_aRGB = pixel.getRGB();
			int shift = 24;
			for (int j = 0; j < 4; j += 1, shift -= 8) {
				/* Extract the LSB from the pixel's aRGB component. */
				int LSB = (pixel_aRGB >> shift) & 0x1;
				character_binary.append(LSB);
			}
		}

		/* Convert CHARACTER_BINARY from binary to decimal form. */
		return (char) Integer.parseInt(character_binary.toString(), 2);
	}

	public static void main(String[] args) {
		System.out.println("K02 Steganographie Text in einem Bild verstecken\n");
		Scanner sc = new Scanner(System.in);
		final String menu = "Verf�gbare Optionen: \n  - [V]erstecke eine Nachricht in einem Bild\n  - [L]ese eine versteckte Nachricht aus einem Bild\n  - [E]nde";
		String input = "";

		/* Print the program menu until the user decides to exit. */
		while (input.length() == 0 || input.charAt(0) != 'E') {
			System.out.println(menu);
			System.out.print("W�hlen Sie eine Option: ");
			input = sc.nextLine();
			if (input.length() > 0) {
				input = input.toUpperCase();
				if (input.charAt(0) == 'V') {
					hide_message(sc);
				} else if (input.charAt(0) == 'L') {
					read_message(sc);
				}
			}
		}
	}
}