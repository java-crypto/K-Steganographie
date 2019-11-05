package net.bplaced.javacrypto.steganography;

/*
* Herkunft/Origin: http://javacrypto.bplaced.net/
* Programmierer/Programmer: Michael Fehr
* Copyright/Copyright: frei verwendbares Programm (Public Domain)
* Copyright: This is free and unencumbered software released into the public domain.
* Lizenttext/Licence: <http://unlicense.org>
* getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
* getestet mit/tested with: Java Runtime Environment 11.0.1 x64
* Datum/Date (dd.mm.jjjj): 05.11.2019
* Funktion: gibt die Ascii-Codes eines Textes im Hex-Format aus
* Function: output Ascii-codes in Hex-format of a text
*
* Sicherheitshinweis/Security notice
* Die Programmroutinen dienen nur der Darstellung und haben keinen Anspruch auf eine korrekte Funktion, 
* insbesondere mit Blick auf die Sicherheit ! 
* Prüfen Sie die Sicherheit bevor das Programm in der echten Welt eingesetzt wird.
* The program routines just show the function but please be aware of the security part - 
* check yourself before using in the real world !
* 
* Das verwendete Zitat stammt aus dem Buch "Per Anhalter durch die Galaxis" von Douglas Adams.
* The text used is taken from the book "The Hitchhiker's Guide to the Galaxy" from Douglas Adams. 
* 
*/


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class k01_anhalter {

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("K01 Anhalter\n");
		System.out.println(
				"Das folgende Zitat wurde dem Buch Per Anhalter durch die Galaxis von Douglas Adams entnommen.\n");
		String anhalter;
		anhalter = "„Der Reiseführer Per Anhalter durch die Galaxis definiert die Marketing-Abteilung der Sirius-Kybernetik-Corporation als »ein Rudel hirnloser Irrer, die als erste an die Wand gestellt werden, wenn die Revolution kommt«. Komischerweise definierte ein Exemplar der Encyclopaedia Galactica, das das große Glück hatte, aus der tausend Jahre entfernten Zukunft herauszufallen, die Marketing-Abteilung der Sirius-Kybernetik-Corporation als »ein Rudel hirnloser Irrer, die als erste an die Wand gestellt wurden, als die Revolution kam«.“\r\n"
				+ " Per Anhalter durch die Galaxis, Kapitel 11";
		byte[] anhalterb = anhalter.getBytes(Charset.forName("UTF-8"));
		String anhalters = new String(anhalterb, "UTF-8");
		System.out.println(anhalters);
		byteArrayPrint(anhalterb);
	}

	public static void byteArrayPrint(byte[] byteData) {
		String rawString = printHexBinary(byteData);
		int rawLength = rawString.length();
		// System.out.println("Daten im Hex-Format");
		int i = 0;
		int j = 1;
		int z = 0;
		for (i = 0; i < rawLength; i++) {
			z++;
			System.out.print(rawString.charAt(i));
			if (j == 2) {
				System.out.print(" ");
				j = 0;
			}
			j++;
			if (z == 90) {
				System.out.println("");
				z = 0;
			}
		}
		System.out.println("");

	}

	public static String printHexBinary(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
