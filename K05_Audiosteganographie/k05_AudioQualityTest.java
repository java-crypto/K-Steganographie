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
import net.bplaced.javacrypto.steganography.k05.audio.*;
import jm.util.*;

public final class k05_AudioQualityTest {
	public static void main(String[] args) throws Exception {
        	/*float[] dataFloat = Read.audio("/Users/kklin/development/java-audio-steganography/test_file.wav");     	
        	double[] data = new double[dataFloat.length];
        	for (int i = 0 ; i<dataFloat.length ; i++) {
        		data[i] = (double) dataFloat[i];
        	}
        	data = FFT.correctDataLength(data);
			Complex[] complexData = new Complex[data.length/2];
			for (int i = 0 ; i<data.length ; i+=2) {
				complexData[i/2] = new Complex(data[i], 0);
			}
			Complex[] complexMags = FFT.fft(complexData);
					
			//take the IFFT
			Complex[] ifft = FFT.ifft(complexMags);

			//change ifft data from complex to real. put in fft class?
			float[] ifftReal = new float[ifft.length]; 
			for (int i = 0 ; i < ifftReal.length ; i++) {
				ifftReal[i] = (float) ifft[i].re();
			}

        	Write.audio(ifftReal, "/Users/kklin/development/java-audio-steganography/test_file_out.wav");*/
        File audioFile = new File(args[0]);

    	/*float[] dataFloat = Read.audio(audioFile.getAbsolutePath());     	
    	double[] audioData = new double[dataFloat.length];
    	for (int i = 0 ; i<dataFloat.length ; i++) {
    		audioData[i] = (double) dataFloat[i];
    	}*/

		AudioSampleReader sampleReader = new AudioSampleReader(audioFile);
    	int nbChannels = sampleReader.getFormat().getChannels();
		int totalBytes = (int) sampleReader.getSampleCount()*nbChannels;
		int bytesToRead=4096*2; //some aribituary number thats 2^n
   		double[] audioData = new double[totalBytes];
    	sampleReader.getInterleavedSamples(0, totalBytes, audioData);
    	for (int i = 0 ; i<audioData.length ; i++) {
    		System.out.println(audioData[i]);
    	}
    }

}