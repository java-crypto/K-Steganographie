package net.bplaced.javacrypto.steganography.k06.ui;


public class SelectOutputFile extends WizardPanel {
    
    java.io.File outputFile;
    
    /** Creates new form SelectOutputFile */
    public SelectOutputFile(String stepText) {
        super(stepText);
        initComponents();
        setFirstFocusable(selectButton);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        selectFileLabel = new javax.swing.JLabel();
        selectFilePanel = new javax.swing.JPanel();
        imageTextField = new javax.swing.JTextField();
        selectButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        selectFileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectFileLabel.setText("Enter the output file name");
        selectFileLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2));
        add(selectFileLabel, java.awt.BorderLayout.CENTER);

        selectFilePanel.setLayout(new java.awt.BorderLayout());

        selectFilePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2));
        selectFilePanel.add(imageTextField, java.awt.BorderLayout.CENTER);

        selectButton.setMnemonic('D');
        selectButton.setText("Select Directory");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        selectFilePanel.add(selectButton, java.awt.BorderLayout.EAST);

        add(selectFilePanel, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents
    
    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        // Add your handling code here:
        // Add your handling code here:
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser(".");     
        if(imageTextField.getText() != null)
            chooser.setSelectedFile(new java.io.File(imageTextField.getText()));
        chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        if( chooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION){
            outputFile = chooser.getSelectedFile();
            outputFile = new java.io.File(outputFile.getAbsolutePath()+System.getProperty("file.separator")+"secretText.txt");
            imageTextField.setText(outputFile.getAbsolutePath());
            imageTextField.select( imageTextField.getText().indexOf("secretText.txt") , imageTextField.getText().length() );
            imageTextField.requestFocus();
        }
    }//GEN-LAST:event_selectButtonActionPerformed
    
    public boolean doValidation() {
        if( imageTextField.getText().length() == 0 ) {
            javax.swing.JOptionPane.showMessageDialog(this,"Select an output file name");
            selectButton.requestFocus();
            return false;
        }else if( ! new java.io.File(imageTextField.getText()).getParentFile().exists()  ){
            javax.swing.JOptionPane.showMessageDialog(this,"Parent directory does not exist!");
            selectButton.requestFocus();
            return false;
        }else if( new java.io.File(imageTextField.getText()).isDirectory()  ){
            javax.swing.JOptionPane.showMessageDialog(this,"Enter an output file name");
            selectButton.requestFocus();
            return false;
        }else if(  new java.io.File(imageTextField.getText()).exists()  ){
            int optionType = javax.swing.JOptionPane.showConfirmDialog(this,"File already exists! Overwrite?","Warning!",javax.swing.JOptionPane.YES_NO_OPTION);
            if( optionType == javax.swing.JOptionPane.NO_OPTION ) return false;
            selectButton.requestFocus();            
        }
        return true;
    }
    
    /** Getter for property outputDirectory.
     * @return Value of property outputDirectory.
     */
    public java.io.File getOutputFile() {
        return outputFile;
    }        
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel selectFileLabel;
    private javax.swing.JTextField imageTextField;
    private javax.swing.JButton selectButton;
    private javax.swing.JPanel selectFilePanel;
    // End of variables declaration//GEN-END:variables
    
}
