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

package net.bplaced.javacrypto.steganography.k08.generator;

import javax.swing.JComponent;

/**
 * This exception is raised by a generator when the input data contains errors.
 * Use the getMessage() method to get the error message to display to the user.
 */
@SuppressWarnings("serial")
public class GeneratorException extends Exception {
	private JComponent widget;
	public GeneratorException(String msg, JComponent w) {
		super(msg);
		widget = w;
	}
	public JComponent getWidget() {
		return widget;
	}
}
