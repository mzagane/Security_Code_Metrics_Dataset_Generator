/**
 * ZM J Code Metrics
 * 
 * file : The main window (work space)
 * src version: 26.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */

package com.zm.sec_code_mets_dataset_gen;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



/**
 *
 * @author ZM
 */
public class Main_JFrame extends javax.swing.JFrame {
       
    /**
     * Creates new form Main_JFrame
     */
    public Main_JFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Project_Src_jFileChooser = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        Project_Source_jTextField = new javax.swing.JTextField();
        Calculate_Metrics_jButton = new javax.swing.JButton();
        Close_jButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Project_Name_jTextField = new javax.swing.JTextField();
        Brows_For_Project_Src_jButton = new javax.swing.JButton();

        Project_Src_jFileChooser.setCurrentDirectory(new java.io.File("C:\\Program Files\\NetBeans-14"));
        Project_Src_jFileChooser.setDialogTitle("Please select the source file or folder");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Poject Source :");

        Project_Source_jTextField.setText("E:\\ZM\\Research\\My_Works\\Current_Works\\Vulnerabilities_Prediction\\Using_Security-specific_Code_Metrics_to_Predict_C-Cpp_Code_Vulnerabilities\\Tools\\Data_Preparation\\My_Dataset_Generation\\Security_Code_Metrics_Dataset_Generator\\tests2");

        Calculate_Metrics_jButton.setText("Calculate Metrics and Generate Dataset");
        Calculate_Metrics_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Calculate_Metrics_jButtonActionPerformed(evt);
            }
        });

        Close_jButton.setText("Close");
        Close_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Close_jButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Project Name :");

        Project_Name_jTextField.setText("Project1");

        Brows_For_Project_Src_jButton.setText("Browse...");
        Brows_For_Project_Src_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Brows_For_Project_Src_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Project_Name_jTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Project_Source_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 235, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Close_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Calculate_Metrics_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 230, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Brows_For_Project_Src_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Project_Name_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(Project_Source_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Brows_For_Project_Src_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(Calculate_Metrics_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Close_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Calculate_Metrics_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Calculate_Metrics_jButtonActionPerformed
                
        String srcML_EXE_Path;
        
        Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Starting...");
        // Loading App settingd
        Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Loading config...");        
        Config App_Settings = new Config(); 
        App_Settings.Load_App_Settings();
        srcML_EXE_Path = App_Settings.App_Settings_Props.getProperty("srcML_EXE_Path");
	Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Loading config...OK"); 
        // end : Load the app settings
        
        //checking inputs
        
        if ( "".equals(Project_Name_jTextField.getText() ))
        {
            JOptionPane.showMessageDialog(this, "Please give valid name for your project!");
            return;
        }
        // TODO: check if the entered path is a valid path
        if ( "".equals(Project_Source_jTextField.getText()))
        {
            JOptionPane.showMessageDialog(this, "Please indicate the full path of your project source (file, archive or containing folder)!");
            return;
        }
        // end : checking inputs
        
        Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Initializing Project..."); 
        // creating a new project and setting its parameters
        Project New_Project = new Project ();
        New_Project.setName(Project_Name_jTextField.getText());
        New_Project.setSource(Project_Source_jTextField.getText());
        New_Project.setXML_File("srcML_xml_file.xml");/* the output xml file 
        generated by srcML*/
        
        Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Initializing Project...OK"); 
        // end project setup
        
        try
        { 
            Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Starting srcML..."); 
            // convert project source to XML using srcML
            ProcessBuilder processBuilder = new ProcessBuilder(srcML_EXE_Path, New_Project.getSource(), "-o"+ New_Project.getXML_File(),"--position" );            
            Process Pros = processBuilder.start();
            Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Starting srcML...OK"); 
            Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Waiting for srcML to generate XML file..."); 
            Pros.waitFor(); // waiting for srcML to generate XML file
            Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Waiting for srcML to generate XML file...OK"); 
            Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Calculating metrics, please wait..."); 
            New_Project.Init(); // processing (get files, function and their metrics)
            Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "Calculating metrics...OK"); 
            Utils.Generate_Dataset(New_Project, "ALL", "arff", "My_Dataset", null, true);
            Logger.getLogger(Main_JFrame.class.getName()).log(Level.INFO, "DONE!!!"); 
       
        } 
        catch (IOException | InterruptedException ex)
        {
        
            Logger.getLogger(Main_JFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_Calculate_Metrics_jButtonActionPerformed

    private void Close_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Close_jButtonActionPerformed

        this.dispose();
    }//GEN-LAST:event_Close_jButtonActionPerformed

    private void Brows_For_Project_Src_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Brows_For_Project_Src_jButtonActionPerformed
               
        Project_Src_jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = Project_Src_jFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = Project_Src_jFileChooser.getSelectedFile();
            Project_Source_jTextField.setText(file.getAbsolutePath());

        }
    }//GEN-LAST:event_Brows_For_Project_Src_jButtonActionPerformed

    /**
     * @param args the command line arguments
     * TODO : write implement the use of the app via command line 
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_JFrame().setVisible(true);
            }
        });
        
        // debugging 
        //System.out.println("just a test");
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Brows_For_Project_Src_jButton;
    private javax.swing.JButton Calculate_Metrics_jButton;
    private javax.swing.JButton Close_jButton;
    private javax.swing.JTextField Project_Name_jTextField;
    private javax.swing.JTextField Project_Source_jTextField;
    private javax.swing.JFileChooser Project_Src_jFileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
