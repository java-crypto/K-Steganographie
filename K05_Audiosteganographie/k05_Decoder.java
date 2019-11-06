package net.bplaced.javacrypto.steganography.k05;

/*
* Herkunft/Origin: http://javacrypto.bplaced.net/
* Programmierer/Programmer: Kevin Lin
* Copyright/Copyright: nicht angegeben
* Copyright: not named.
* Lizenztext/Licence: Apache 2.0, http://www.apache.org/licenses/LICENSE-2.0
* getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
* getestet mit/tested with: Java Runtime Environment 11.0.1 x64
* Datum/Date (dd.mm.jjjj): 05.11.2019
* Projekt/Project: K05 Text aus einer Audiodatei auslesen
*                  K05 Decode text from an audiofile
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
* https://github.com/abhijeet-adarsh/Steganography
* 
* Das Programm benötigt die nachfolgende Bibliothek (siehe Github Archiv):
* The programm uses this external library (see Github Archive):
* jar-Datei: jMusic1.6.4.jar 
* jar-Datei: https://mvnrepository.com/artifact/com.explodingart/jmusic
* 
*  Beispiel für einen Kommandozeilenaufruf / Example for command line excecution: 
*  "f://k05_flowing-water_freewavesamples_com.wav"
*/

import net.bplaced.javacrypto.steganography.k05.audio.AudioSampleReader;
import net.bplaced.javacrypto.steganography.k05.fourier.FFT;
import net.bplaced.javacrypto.steganography.k05.fourier.FFTData;
import net.bplaced.javacrypto.steganography.k05.fourier.FFTDataAnalyzer;
import net.bplaced.javacrypto.steganography.k05.binary.Binary;
import net.bplaced.javacrypto.steganography.k05.binary.BinaryTool;
import java.io.*;
import javax.sound.sampled.*;

public class k05_Decoder {
	File audioFile;

	public k05_Decoder(File audioFile) {
		this.audioFile = audioFile;
	}

	public String decodeMessage() {
		String hiddenMessage = "";
		try {
			AudioSampleReader sampleReader = new AudioSampleReader(audioFile);
			int bytesRead = 0;
			int nbChannels = sampleReader.getFormat().getChannels();
			int totalBytes = (int) sampleReader.getSampleCount() * nbChannels;
			int bytesToRead = 4096 * 2; // some aribituary number thats 2^n
			StringBuilder messageAsBytes = new StringBuilder(totalBytes / bytesToRead);

			double[] audioData = new double[totalBytes];
			sampleReader.getInterleavedSamples(0, totalBytes, audioData);
			while (bytesRead < totalBytes) {
				if (totalBytes - bytesRead < bytesToRead) {
					bytesToRead = totalBytes - bytesRead;
				}

				// read in the data
				double[] samples = new double[bytesToRead];
				for (int i = 0; i < samples.length; i++) {
					samples[i] = audioData[bytesRead + i];
				}
				bytesRead += bytesToRead;
				double[] channelOne = new double[samples.length / 2];
				sampleReader.getChannelSamples(0, samples, channelOne);

				// take the FFT
				channelOne = FFT.correctDataLength(channelOne);
				// double[][] freqMag = FFT.getMag(channelOne, (int)
				// sampleReader.getFormat().getFrameRate());
				FFTData[] fftData = FFT.getMag(channelOne, (int) sampleReader.getFormat().getFrameRate());

				FFTDataAnalyzer analyzer = new FFTDataAnalyzer(fftData);
				boolean isRest = analyzer.isRest();

				double ampToTest = 0; // TODO: rename
				if (!isRest) {
					ampToTest = analyzer.getMagnitudeOfFrequency(20000); // TODO: don't hardcode frequency
				}

				if (!isRest) {
					// compare the overtones to see if there should be a 1 or 0
					// int overtoneToTest = overtones.length;
					// if
					// (Math.abs(overtones[overtoneToTest-1][1]-expectedOvertones[overtoneToTest-1])>.0049)
					// {
					if (ampToTest > .0009) { // just test a certain freq
						// checking if something is null..
						messageAsBytes.append("1");
					} else {
						messageAsBytes.append("0");
					}
					int bitsSaved = messageAsBytes.length();
					if (bitsSaved % 8 == 0 && bitsSaved != 0) {
						if (messageAsBytes.toString().substring(bitsSaved - 8, bitsSaved).equals("00000000")) { // if
																												// null
							// System.out.println("The message is over.");
							// break; //the message is done
						}
					}
				}
			}

			hiddenMessage = constructMessage(messageAsBytes.toString());
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hiddenMessage;
	}

	private static String constructMessage(String messageInBinary) {
		return BinaryTool.binaryToASCII(new Binary(messageInBinary));
	}

	public static void main(String args[]) {
		String filePath = args[0];
		k05_Decoder decoder = new k05_Decoder(new File(filePath));
		System.out.println("The hidden message was: " + decoder.decodeMessage());
	}
}
