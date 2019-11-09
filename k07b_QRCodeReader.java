package net.bplaced.javacrypto.steganography;

/*
* Herkunft/Origin: http://javacrypto.bplaced.net/
* Programmierer/Programmer: Rajeev Singh
* Copyright/Copyright: nicht angegeben
* Copyright: not named.
* Lizenztext/Licence: Apache 2.0, http://www.apache.org/licenses/LICENSE-2.0
* getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
* getestet mit/tested with: Java Runtime Environment 11.0.1 x64
* Datum/Date (dd.mm.jjjj): 09.11.2019
* Projekt/Project: K07b QRCode Leser
*                  K07b QR Code Decoder
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
* https://github.com/callicoder/qr-code-generator-and-reader
* https://www.callicoder.com/qr-code-reader-scanner-in-java-using-zxing/
* 
* Das Programm benötigt die nachfolgenden Bibliotheken (siehe Github Archiv):
* The programm uses these external libraries (see Github Archive):
* jar-Datei: core-3.4.0.jar
* jar File: https://repo1.maven.org/maven2/com/google/zxing/core/
* jar-Datei: javase-3.4.0.jar
* jar File: https://repo1.maven.org/maven2/com/google/zxing/javase/
* 
* Das Wort QR Code ist ein eingetragenes Warenzeichen der DENSO WAVE INCORPORATED
* Lizenztext: http://www.denso-wave.com/qrcode/faqpatent-e.html
* 
*/

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class k07b_QRCodeReader {
	private static final String QR_CODE_IMAGE_PATH = "f://k07_qrcode.png";

	private static String decodeQRCode(File qrCodeimage) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		try {
			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			System.out.println("There is no QR code in the image");
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println("K07b Lese einen QR Code aus");
		System.out.println("\nDas Wort QR Code ist ein eingetragenes Warenzeichen der DENSO WAVE INCORPORATED\r\n"
				+ "Lizenztext: http://www.denso-wave.com/qrcode/faqpatent-e.html\n");
		try {

			File file = new File(QR_CODE_IMAGE_PATH);
			String decodedText = decodeQRCode(file);
			if (decodedText == null) {
				// System.out.println("No QR Code found in the image");
				System.out.println("Es befindet sich kein QR Code im Bild " + QR_CODE_IMAGE_PATH);
			} else {
				// System.out.println("Decoded text = " + decodedText);
				System.out.println("Der QR-Code lautet: " + decodedText);
			}
		} catch (IOException e) {
			// System.out.println("Could not decode QR Code, IOException :: " +
			// e.getMessage());
			System.out.println("Fehler bei der Dekodierung des QR Codes im Bild: " + QR_CODE_IMAGE_PATH
					+ " ,IOException : " + e.getMessage());
		}
	}
}