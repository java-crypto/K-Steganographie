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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class EmailGenerator implements GeneratorInterface {
	private JPanel panel = new JPanel();
	private JTextField txtEmail = new JTextField();
	
	public EmailGenerator() {
		SpringLayout layout = new SpringLayout();
		JLabel label = new JLabel("E-Mail: ");
		
		panel.setLayout(layout);
		panel.add(label);
		panel.add(txtEmail);
		
		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, panel);
		
		layout.putConstraint(SpringLayout.WEST, txtEmail, 5, SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.NORTH, txtEmail, 5, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.EAST, txtEmail, -5, SpringLayout.EAST, panel);
	}

	@Override
	public String getName() {
		return "E-Mail";
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public String getText() throws GeneratorException {
		String uri = txtEmail.getText();
		if (uri.isEmpty())
			throw new GeneratorException("E-Mail cannot be empty.", txtEmail);
		if (!Validator.isValidEmail(uri))
			throw new GeneratorException("Incorrect E-Mail", txtEmail);
		return "mailto:" + uri;
	}

	@Override
	public void setFocus() {
		txtEmail.requestFocusInWindow();
	}

	@Override
	public int getParsingPriority() {
		return 1;
	}

	@Override
	public boolean parseText(String text, boolean write) {
		String captext = text.toUpperCase();
		if (!captext.matches("^MAILTO:.*$"))
			return false;
		
		String email = text.split(":")[1].trim();
		if (!Validator.isValidEmail(email))
			return false;
		
		if (write)
			txtEmail.setText(email);
		
		return true;
	}

}
