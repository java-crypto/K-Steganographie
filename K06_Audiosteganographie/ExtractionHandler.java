package net.bplaced.javacrypto.steganography.k06;

// gehört zu / belongs to: k06_audiosteganography

import net.bplaced.javacrypto.steganography.k06.ui.EmbedExtractOptions;

public class ExtractionHandler {

	public static void startExtract(EmbedExtractOptions eo) {

		System.out.println("Extracting....");
		String embFileName = eo.getOutputFile().getAbsolutePath();
		String password = eo.getPassword();
		System.out
				.println("Audio File: " + eo.getInputFile().getAbsolutePath());
		System.out.println("File: " + embFileName);
		System.out.println("Password: " + password);

		SteganographyHandler handler = new SteganographyHandler(eo
				.getInputFile().getAbsolutePath(), embFileName,
				password.toCharArray());
		if (!handler.extract())
			System.out
					.println("Error occured during decrypt!, may be the message is too big.");

		System.out.println("Extraction process is completed.");

	}

}
