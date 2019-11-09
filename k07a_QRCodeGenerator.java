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
* Projekt/Project: K07a QRCode Generator
*                  K07a QR Code Generator
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
* https://www.callicoder.com/generate-qr-code-in-java-using-zxing/
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by rajeevkumarsingh on 22/08/17.
 */
public class k07a_QRCodeGenerator {
	private static final String QR_CODE_IMAGE_PATH = "f://k07_qrcode.png";
	private static final String QR_CODE_TEXT = "K07 QR-Code im Bild";

	private static void generateQRCodeImage(String text, int width, int height, String filePath)
			throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	}

	private byte[] getQRCodeImageByteArray(String text, int width, int height) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		byte[] pngData = pngOutputStream.toByteArray();
		return pngData;
	}

	public static void main(String[] args) {
		System.out.println("K07a Erzeuge einen QR Code");
		System.out.println("\nDas Wort QR Code ist ein eingetragenes Warenzeichen der DENSO WAVE INCORPORATED\r\n"
				+ "Lizenztext: http://www.denso-wave.com/qrcode/faqpatent-e.html\n");
		try {
			generateQRCodeImage(QR_CODE_TEXT, 350, 350, QR_CODE_IMAGE_PATH);
		} catch (WriterException e) {
			// System.out.println("Could not generate QR Code, WriterException :: " +
			// e.getMessage());
			System.out.println("Fehler - konnte den QR-Code nicht erzeugen, Fehlermeldung: " + e.getMessage());
		} catch (IOException e) {
			// System.out.println("Could not generate QR Code, IOException :: " +
			// e.getMessage());
			System.out.println("Fehler - Konto die QR-Code-Datei nicht erzeugen, Fehlermeldung: " + e.getMessage());
		}
		System.out.println("Dieser Text wurde in das QR-Bild kodiert: " + QR_CODE_TEXT);
		System.out.println("Diese Datei wurde erzeugt: " + QR_CODE_IMAGE_PATH);
	}
}
