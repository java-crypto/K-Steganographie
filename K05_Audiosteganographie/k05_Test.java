/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.bplaced.javacrypto.steganography.k05;

/*
* Herkunft/Origin: http://javacrypto.bplaced.net/
* Programmierer/Programmer: Kevin Lin
* Copyright/Copyright: nicht angegeben
* Copyright: not named.
* Lizenztext/Licence: Apache 2.0, http://www.apache.org/licenses/LICENSE-2.0
* 
* Projekt/Project: K05 Audiosteganographie
*                  K05 Audiosteganography
*
* Das Projekt basiert auf dem nachfolgenden Github-Archiv des Autors:
* The project is based this Github-Archive of the Author:
* https://github.com/abhijeet-adarsh/Steganography
* 
* Dieses Programm wurde von javacrypto.bplaced.net nicht getestet.
* This program was not tested by javacrypto.bplaced.net.
* 
*/

import java.io.File;
import net.bplaced.javacrypto.steganography.k05.audio.AudioSampleReader;
import net.bplaced.javacrypto.steganography.k05.audio.AudioSampleWriter;
import net.bplaced.javacrypto.steganography.k05.audio.AudioTool;
import javax.sound.sampled.*;
import net.bplaced.javacrypto.steganography.k05.fourier.Complex;
import net.bplaced.javacrypto.steganography.k05.fourier.FFT;

/**
 *
 * @author kklin
 */
public class k05_Test {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String message = args[0];
		String filePath = args[1];
		String outPath = filePath.substring(0, filePath.length() - 4) + "-Encoded.wav";
		k05_Encoder encoder = new k05_Encoder(new File(filePath));
		encoder.encodeMessage(message, outPath);
		System.out.println("Successfully encoded the message into " + outPath);

		System.out.println("Beginning decode");
		k05_Decoder decoder = new k05_Decoder(new File(outPath));
		System.out.println("The hidden message was: " + decoder.decodeMessage());
	}
}
