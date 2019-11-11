
package net.bplaced.javacrypto.steganography.k08;

/*
* Herkunft/Origin: http://javacrypto.bplaced.net/
* Programmierer/Programmer: Che-Huai Lin
* Copyright: 2012 Timothy Lin <lzh9102@gmail.com
* Copyright: 2012 Timothy Lin <lzh9102@gmail.com
* Lizenztext/Licence: Apache 2.0, http://www.apache.org/licenses/LICENSE-2.0
* getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
* getestet mit/tested with: Java Runtime Environment 11.0.1 x64
* Datum/Date (dd.mm.jjjj): 11.11.2019
* Projekt/Project: K08 Daten in einem QR-Code-Bild einbetten mit GUI
*                  K08 Embedd data in a QR-Code-picture with GUI
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
* https://github.com/lzh9102/qrcode-desktop
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

/*
 * Copyright 2012 Timothy Lin <lzh9102@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.swing.SwingUtilities;

public class k08_Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println("Test");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainWindow window = new MainWindow();
				window.setVisible(true);
			}
		});
	}

}
