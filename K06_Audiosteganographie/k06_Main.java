/**
 * 
 */
package net.bplaced.javacrypto.steganography.k06;

/*
* Herkunft/Origin: http://javacrypto.bplaced.net/
* Programmierer/Programmer: Mohamed Talaat
* Webseite/Website: http://www.mohamedtalaat.net/
* Copyright/Copyright: nicht angegeben/not named
* Lizenztext/Licence: nicht angegeben/not named
* getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
* getestet mit/tested with: Java Runtime Environment 11.0.1 x64
* Datum/Date (dd.mm.jjjj): 06.11.2019
* Projekt/Project: K06 Text in einer Audiodatei verstecken (mit GUI)
*                  K06 Hide text in an audiofile (with GUI)
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
* https://github.com/mtala3t/Audio-Steganography
* 
*/

import net.bplaced.javacrypto.steganography.k06.ui.MainWindow;

/**
 * @author mtalaat
 *
 */
public class k06_Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		new MainWindow().show();

	}

}
