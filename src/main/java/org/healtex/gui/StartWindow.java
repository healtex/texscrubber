/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.healtex.gui;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

/**
 *
 * @author rich
 */
public class StartWindow extends javax.swing.JFrame {

        class RunBatch extends SwingWorker<Void, Void> implements PropertyChangeListener{
            RunBatch(int DocCount){
                jProgressBarBatchProgress.setMaximum(DocCount);
            }
            
            @Override
            public Void doInBackground() {
                int progress = 0;
                setProgress(0);
                while (progress < 5) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) {}
                    progress += 1;
                    setProgress(progress);
                }
                return null;
            }
            @Override
            public void done() {
                jButtonLaunchBatch.setEnabled(true); 
                jButtonCancelBatch.setEnabled(false); 
                setCursor(null); //turn off the wait cursor
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
            if ("progress" == evt.getPropertyName()) {
                int progress = (Integer) evt.getNewValue();
                jProgressBarBatchProgress.setValue(progress);

            } 
        }
    }
    
    
    public void updateTextFieldWithFileChooserInfo(javax.swing.JTextField tf){
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(StartWindow.this);
        
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                tf.setText(file.getCanonicalPath());
                //This is where a real application would open the file.
                //log.append("Opening: " + file.getName() + "." + newline);
            } catch (IOException ex) {
                Logger.getLogger(StartWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //log.append("Open command cancelled by user." + newline);
        }                
    }
    
    private void goToCard(String card) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), card);
    }    
    
    /**
     * Creates new form StartWindow
     */
    public StartWindow() {
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
        java.awt.GridBagConstraints gridBagConstraints;

        homeCard = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonGoToBatchProcessingView = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        batchLauncherCard = new javax.swing.JPanel();
        jButtonSelInputDir = new javax.swing.JButton();
        jTextFieldRegEx = new javax.swing.JTextField();
        jTextFieldInputDir = new javax.swing.JTextField();
        jButtonSelOutputDir = new javax.swing.JButton();
        jTextFieldOutputDir = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonLaunchBatch = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanelBatchProgress = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jProgressBarBatchProgress = new javax.swing.JProgressBar();
        jButtonCancelBatch = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuGoTo = new javax.swing.JMenu();
        jMenuItemHomeCard = new javax.swing.JMenuItem();
        jMenuItemBatchCard = new javax.swing.JMenuItem();
        jMenuItemResultsCard = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        homeCard.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(250, 468));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        homeCard.add(jPanel5, java.awt.BorderLayout.LINE_START);

        jPanel4.setPreferredSize(new java.awt.Dimension(250, 468));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        homeCard.add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel3.setPreferredSize(new java.awt.Dimension(967, 150));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        homeCard.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel2.setPreferredSize(new java.awt.Dimension(967, 150));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        homeCard.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.GridLayout());

        jButtonGoToBatchProcessingView.setText("Run TexScrubber Batches");
        jButtonGoToBatchProcessingView.setActionCommand("goToBatchRunner");
        jButtonGoToBatchProcessingView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGoToBatchProcessingViewActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonGoToBatchProcessingView);

        jButton2.setText("View Scrubbed Documents");
        jButton2.setActionCommand("goToDocumentViewer");
        jPanel1.add(jButton2);

        homeCard.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(homeCard, "homeCard");

        java.awt.GridBagLayout batchLauncherCardLayout = new java.awt.GridBagLayout();
        batchLauncherCardLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        batchLauncherCardLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        batchLauncherCard.setLayout(batchLauncherCardLayout);

        jButtonSelInputDir.setText("Select input directory");
        jButtonSelInputDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelInputDirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        batchLauncherCard.add(jButtonSelInputDir, gridBagConstraints);

        jTextFieldRegEx.setText("regex pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        batchLauncherCard.add(jTextFieldRegEx, gridBagConstraints);

        jTextFieldInputDir.setText("input dir");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 35;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        batchLauncherCard.add(jTextFieldInputDir, gridBagConstraints);

        jButtonSelOutputDir.setText("Select Output Directory");
        jButtonSelOutputDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelOutputDirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        batchLauncherCard.add(jButtonSelOutputDir, gridBagConstraints);

        jTextFieldOutputDir.setText("output dir");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 37;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        batchLauncherCard.add(jTextFieldOutputDir, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridLayout(4, 0));

        jCheckBox1.setText("Names");
        jPanel6.add(jCheckBox1);

        jCheckBox2.setText("Addresses");
        jPanel6.add(jCheckBox2);

        jCheckBox3.setText("Telephone Numbers");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel6.add(jCheckBox3);

        jCheckBox4.setText("Postcodes");
        jPanel6.add(jCheckBox4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 9;
        batchLauncherCard.add(jPanel6, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridLayout(2, 0));

        jCheckBox5.setText("XML");
        jPanel7.add(jCheckBox5);

        jCheckBox6.setText("Plain Text");
        jPanel7.add(jCheckBox6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridheight = 3;
        batchLauncherCard.add(jPanel7, gridBagConstraints);

        jLabel1.setText("Select Fields to Scrub");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 9;
        batchLauncherCard.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Seelct Output Formats");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridheight = 3;
        batchLauncherCard.add(jLabel2, gridBagConstraints);

        jButtonLaunchBatch.setText("Launch TexScrubber");
        jButtonLaunchBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLaunchBatchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        batchLauncherCard.add(jButtonLaunchBatch, gridBagConstraints);

        jLabel3.setText("Input Dir");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        batchLauncherCard.add(jLabel3, gridBagConstraints);

        jLabel4.setText("RegEx Pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        batchLauncherCard.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Output Dir");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        batchLauncherCard.add(jLabel5, gridBagConstraints);

        jPanelBatchProgress.setLayout(new java.awt.GridLayout(3, 0));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Scrubbing Docs");
        jPanelBatchProgress.add(jLabel6);
        jPanelBatchProgress.add(jProgressBarBatchProgress);

        jButtonCancelBatch.setText("Cancel");
        jPanelBatchProgress.add(jButtonCancelBatch);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 34;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        batchLauncherCard.add(jPanelBatchProgress, gridBagConstraints);

        getContentPane().add(batchLauncherCard, "batchCard");

        jMenuGoTo.setText("Go To");

        jMenuItemHomeCard.setText("Home");
        jMenuItemHomeCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHomeCardActionPerformed(evt);
            }
        });
        jMenuGoTo.add(jMenuItemHomeCard);

        jMenuItemBatchCard.setText("Run Batches");
        jMenuItemBatchCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBatchCardActionPerformed(evt);
            }
        });
        jMenuGoTo.add(jMenuItemBatchCard);

        jMenuItemResultsCard.setText("View Results");
        jMenuItemResultsCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemResultsCardActionPerformed(evt);
            }
        });
        jMenuGoTo.add(jMenuItemResultsCard);

        jMenuBar1.add(jMenuGoTo);
        jMenuGoTo.getAccessibleContext().setAccessibleName("Go To");

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jButtonSelInputDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelInputDirActionPerformed
        // TODO add your handling code here:
        updateTextFieldWithFileChooserInfo(jTextFieldInputDir);
    }//GEN-LAST:event_jButtonSelInputDirActionPerformed

    private void jButtonGoToBatchProcessingViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGoToBatchProcessingViewActionPerformed
        goToCard("batchCard");
    }//GEN-LAST:event_jButtonGoToBatchProcessingViewActionPerformed

    private void jButtonSelOutputDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelOutputDirActionPerformed
        // TODO add your handling code here:
        updateTextFieldWithFileChooserInfo(jTextFieldOutputDir);
    }//GEN-LAST:event_jButtonSelOutputDirActionPerformed

    private void jButtonLaunchBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLaunchBatchActionPerformed
        // TODO add your handling code here:
        jButtonLaunchBatch.setEnabled(false); 
        jButtonCancelBatch.setEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RunBatch runBatch = new RunBatch(5);
        runBatch.addPropertyChangeListener(runBatch);
        runBatch.execute();       
    }//GEN-LAST:event_jButtonLaunchBatchActionPerformed

    private void jMenuItemHomeCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHomeCardActionPerformed
        // TODO add your handling code here:
        goToCard("homeCard");
    }//GEN-LAST:event_jMenuItemHomeCardActionPerformed

    private void jMenuItemBatchCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBatchCardActionPerformed
        // TODO add your handling code here:
        goToCard("batchCard");
    }//GEN-LAST:event_jMenuItemBatchCardActionPerformed

    private void jMenuItemResultsCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemResultsCardActionPerformed
        // TODO add your handling code here:
        goToCard("resultsCard");
    }//GEN-LAST:event_jMenuItemResultsCardActionPerformed

    /**
     * @param args the command line arguments
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
            java.util.logging.Logger.getLogger(StartWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel batchLauncherCard;
    private javax.swing.JPanel homeCard;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonCancelBatch;
    private javax.swing.JButton jButtonGoToBatchProcessingView;
    private javax.swing.JButton jButtonLaunchBatch;
    private javax.swing.JButton jButtonSelInputDir;
    private javax.swing.JButton jButtonSelOutputDir;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuGoTo;
    private javax.swing.JMenuItem jMenuItemBatchCard;
    private javax.swing.JMenuItem jMenuItemHomeCard;
    private javax.swing.JMenuItem jMenuItemResultsCard;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelBatchProgress;
    private javax.swing.JProgressBar jProgressBarBatchProgress;
    private javax.swing.JTextField jTextFieldInputDir;
    private javax.swing.JTextField jTextFieldOutputDir;
    private javax.swing.JTextField jTextFieldRegEx;
    // End of variables declaration//GEN-END:variables


}
