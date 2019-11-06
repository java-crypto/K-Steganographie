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
* Projekt/Project: K05 Text in einer Audiodatei verstecken
*                  K05 Hide text in an audiofile
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
* https://github.com/abhijeet-adarsh/Steganography
* 
* Das Programm ben�tigt die nachfolgende Bibliothek (siehe Github Archiv):
* The programm uses this external library (see Github Archive):
* jar-Datei: jMusic1.6.4.jar 
* jar-Datei: https://mvnrepository.com/artifact/com.explodingart/jmusic
* 
*  Beispiel f�r einen Kommandozeilenaufruf / Example for command line excecution: 
*  "123" "f://k05_flowing-water_freewavesamples_com.wav"
*/

import java.io.*;
import javax.sound.sampled.*;
import net.bplaced.javacrypto.steganography.k05.audio.AudioSampleReader;
import net.bplaced.javacrypto.steganography.k05.audio.AudioSampleWriter;
import net.bplaced.javacrypto.steganography.k05.audio.AudioTool;
import net.bplaced.javacrypto.steganography.k05.fourier.Complex;
import net.bplaced.javacrypto.steganography.k05.fourier.FFT;
import net.bplaced.javacrypto.steganography.k05.fourier.FFTData;
import net.bplaced.javacrypto.steganography.k05.fourier.FFTDataAnalyzer;
import net.bplaced.javacrypto.steganography.k05.binary.BinaryTool;
import jm.util.*;

public class k05_Encoder {
	File audioFile;

	public k05_Encoder(File audioFile) {
		this.audioFile = audioFile;
	}

	public void encodeMessage(String message, String outPath) { // change outPath to File
		int[] messageAsBits = BinaryTool.ASCIIToBinary(message).getIntArray();
		int currentBit = 0;
		float[] dataFloat = Read.audio(audioFile.getAbsolutePath());
		double[] audioData = new double[dataFloat.length];
		for (int i = 0; i < dataFloat.length; i++) {
			audioData[i] = (double) dataFloat[i];
		}
		int bytesRead = 0;
		int totalBytes = audioData.length;
		double[] out = new double[totalBytes];
		int bytesToRead = 4096 * 2; // some aribituary number thats 2^n
		try {
			AudioSampleReader sampleReader = new AudioSampleReader(audioFile);
			/*
			 * int bytesRead = 0; int nbChannels = sampleReader.getFormat().getChannels();
			 * int totalBytes = (int) sampleReader.getSampleCount()*nbChannels; double[] out
			 * = new double[totalBytes]; int bytesToRead=4096*2; //some aribituary number
			 * thats 2^n double[] audioData = new double[totalBytes];
			 * sampleReader.getInterleavedSamples(0, totalBytes, audioData);
			 */

			if (totalBytes / bytesToRead < messageAsBits.length) {
				throw new RuntimeException("The audio file is too short for the message to fit!");
			}

			while (bytesRead < totalBytes && currentBit < messageAsBits.length) {
				if (totalBytes - bytesRead < bytesToRead) {
					bytesToRead = totalBytes - bytesRead;
				}

				// System.out.println("Reading data.");
				// take a portion of the data
				double[] samples = new double[bytesToRead];
				for (int i = 0; i < samples.length; i++) {
					samples[i] = audioData[bytesRead + i];
				}
				bytesRead += bytesToRead;
				double[] channelOne = new double[samples.length / 2];
				for (int i = 0; i < samples.length; i += 2) {
					channelOne[i / 2] = samples[i];
				}
				// sampleReader.getChannelSamples(0, samples, channelOne);
				// System.out.println("Taking the FFT.");
				// take the FFT
				FFTData[] freqMag = FFT.getMag(channelOne, 44100); // TODO: don't hardcode
				FFTDataAnalyzer analyzer = new FFTDataAnalyzer(freqMag);
				boolean isRest = analyzer.isRest();

				channelOne = FFT.correctDataLength(channelOne);
				Complex[] complexData = new Complex[channelOne.length];
				for (int i = 0; i < channelOne.length; i++) {
					complexData[i] = new Complex(channelOne[i], 0);
				}
				Complex[] complexMags = FFT.fft(complexData);
				double[] freqs = FFT.getFreqs(complexData.length, 44100); // TODO: don't hardcode

				// System.out.println("Writing the 1 or 0");
				// decide if the overtone should be changed and if so, change it. don't write if
				// its a rest
				if (messageAsBits[currentBit] == 1 && isRest == false) {
					// edit the data thats going to be ifft'd
					for (int i = 0; i < freqs.length; i++) {
						if (Math.abs(Math.abs(freqs[i]) - 20000) < 5) { // lets try changing a set freq
							complexMags[i] = new Complex(15, 0); // don't hardcode
						}
					}

					// take the IFFT
					Complex[] ifft = FFT.ifft(complexMags);

					// change ifft data from complex to real. put in fft class?
					double[] ifftReal = new double[ifft.length];
					for (int i = 0; i < ifftReal.length; i++) {
						ifftReal[i] = ifft[i].re();
					}

					double[] toWrite = AudioTool.interleaveSamples(ifftReal);
					System.arraycopy(toWrite, 0, out, bytesRead - bytesToRead, toWrite.length); // add to the array
																								// thats going to be
																								// written out
					currentBit++;
				} else if (messageAsBits[currentBit] == 0 && !isRest) {
					// add a 0 to the message
					System.arraycopy(samples, 0, out, bytesRead - bytesToRead, samples.length);
					currentBit++;
				} else if (isRest) { // similar to encoding a zero, but don't increment the bit count
					System.arraycopy(samples, 0, out, bytesRead - bytesToRead, samples.length);
				}
			}

			// writing out the leftover part of the audio file (which doesn't have any
			// encoded btis in it)
			if (bytesRead < totalBytes) {
				double[] leftoverData = new double[totalBytes - bytesRead];
				// take a portion of the data
				for (int i = 0; i < leftoverData.length; i++) {
					leftoverData[i] = audioData[bytesRead + i];
				}
				System.arraycopy(leftoverData, 0, out, bytesRead, leftoverData.length);
			}

			/*
			 * float[] outFloat = new float[out.length]; for (int i = 0 ; i <
			 * outFloat.length ; i++) { outFloat[i] = (float) out[i]; }
			 * Write.audio(outFloat, outPath);
			 */
			File outFile = new File(outPath);
			AudioSampleWriter audioWriter = new AudioSampleWriter(outFile, sampleReader.getFormat(),
					AudioFileFormat.Type.WAVE);
			audioWriter.write(out);
			audioWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		String message = args[0];
		String filePath = args[1];
		String outPath = filePath.substring(0, filePath.length() - 4) + "-Encoded.wav";
		k05_Encoder encoder = new k05_Encoder(new File(filePath));
		encoder.encodeMessage(message, outPath);
		System.out.println("Successfully encoded \"" + message + "\" into " + outPath);
	}
}
