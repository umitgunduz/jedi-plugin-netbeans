/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

import com.jedi.metadata.DatabaseMetadataUtil;
import com.jedi.metadata.PackageMetadata;
import com.jedi.metadata.ProcedureMetadata;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.openide.util.Exceptions;

public final class CreationVisualPanel1 extends JPanel {
    
    ConnectionManager connectionManager = new ConnectionManager();
    
    public void updateView() {
        
    }

    /**
     * Creates new form CreationVisualPanel1
     */
    public CreationVisualPanel1() {
        initComponents();
        listPackages.setCellRenderer(new PackageListCellRenderer());
        listProcedures.setCellRenderer(new ProcedureListCellRenderer());
        
    }
    
    @Override
    public String getName() {
        return "Choose the stored procedure";
    }
    
    private ComboBoxModel getAvailableConnections() {
        return new DefaultComboBoxModel(connectionManager.getConnections());
    }
    
    private void searchPackages() throws SQLException {
        String pattern = txtSearchPackages.getText();
        DefaultListModel model = new DefaultListModel();
        Connection connection = getConnection();
        List<PackageMetadata> list = DatabaseMetadataUtil.searchPackages(pattern, connection);
        if (list != null && !list.isEmpty()) {
            for (PackageMetadata packageMetadata : list) {
                model.addElement(packageMetadata);
            }
        }
        
        listPackages.setModel(model);
        listPackages.setSelectedIndex(-1);
        
    }
    
    private void searchProcedures() {
        try {
            Connection connection = getConnection();
            String pattern = txtSearchProcedures.getText();
            PackageMetadata packageMetadata = null;
            if (listPackages.getSelectedIndex() > -1) {
                if (listPackages.getSelectedValue() instanceof PackageMetadata) {
                    packageMetadata = (PackageMetadata) listPackages.getSelectedValue();
                }
            }
            
            DefaultListModel model = new DefaultListModel();
            
            if (packageMetadata != null) {
                List<ProcedureMetadata> list = DatabaseMetadataUtil.searchProcedures(pattern, connection, packageMetadata);
                if (list != null && !list.isEmpty()) {
                    for (ProcedureMetadata procedureMetadata : list) {
                        model.addElement(procedureMetadata);
                    }
                }
            }
            
            listProcedures.setModel(model);
            listProcedures.setSelectedIndex(-1);
            
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    private Connection getConnection() {
        Connection result = null;
        Object item = comboConnections.getSelectedItem();
        if (item != null && item instanceof DatabaseConnection) {
            DatabaseConnection dc = (DatabaseConnection) item;
            result = dc.getJDBCConnection();
        }
        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connectButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        newConnectionButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        comboConnections = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        listPackages = new javax.swing.JList();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        listProcedures = new javax.swing.JList();
        txtSearchPackages = new javax.swing.JTextField();
        buttonSearchPackage = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSearchProcedures = new javax.swing.JTextField();
        buttonSearchProcedures = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(662, 472));

        org.openide.awt.Mnemonics.setLocalizedText(connectButton, org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.connectButton.text")); // NOI18N
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connect(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(newConnectionButton, org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.newConnectionButton.text")); // NOI18N
        newConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewConnection(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.jLabel1.text")); // NOI18N

        comboConnections.setModel(getAvailableConnections());
        comboConnections.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choseConnection(evt);
            }
        });

        listPackages.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listPackagesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listPackages);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jScrollPane2.setViewportView(listProcedures);

        txtSearchPackages.setText(org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.txtSearchPackages.text")); // NOI18N

        buttonSearchPackage.setIcon(new javax.swing.ImageIcon("E:\\Turkcell\\Umit\\git\\jedi-plugin-netbeans\\resources\\search.png")); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(buttonSearchPackage, org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.buttonSearchPackage.text")); // NOI18N
        buttonSearchPackage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSearchPackageMouseClicked(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.jLabel4.text")); // NOI18N

        txtSearchProcedures.setText(org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.txtSearchProcedures.text")); // NOI18N

        buttonSearchProcedures.setIcon(new javax.swing.ImageIcon("E:\\Turkcell\\Umit\\git\\jedi-plugin-netbeans\\resources\\search.png")); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(buttonSearchProcedures, org.openide.util.NbBundle.getMessage(CreationVisualPanel1.class, "CreationVisualPanel1.buttonSearchProcedures.text")); // NOI18N
        buttonSearchProcedures.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSearchProceduresMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(23, 173, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboConnections, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(connectButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newConnectionButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtSearchPackages)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(buttonSearchPackage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(txtSearchProcedures))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonSearchProcedures, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboConnections, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newConnectionButton)
                    .addComponent(connectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSearchPackages, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonSearchPackage))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonSearchProcedures)
                                    .addComponent(txtSearchProcedures, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator3))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void connect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connect
        
        DatabaseConnection sc = (DatabaseConnection) comboConnections.getSelectedItem();
        if (sc != null) {
            connectionManager.showConnectionDialog(sc);
        }
    }//GEN-LAST:event_connect

    private void createNewConnection(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewConnection
        connectionManager.showAddConnectionDialog(null);
        comboConnections.setModel(getAvailableConnections());

    }//GEN-LAST:event_createNewConnection

    private void choseConnection(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choseConnection
        connectButton.setEnabled(true);
    }//GEN-LAST:event_choseConnection

    private void buttonSearchPackageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSearchPackageMouseClicked
        try {
            searchPackages();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_buttonSearchPackageMouseClicked

    private void listPackagesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listPackagesValueChanged
        if (!evt.getValueIsAdjusting()) {
            searchProcedures();
        }
    }//GEN-LAST:event_listPackagesValueChanged

    private void buttonSearchProceduresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSearchProceduresMouseClicked
        searchProcedures();

    }//GEN-LAST:event_buttonSearchProceduresMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonSearchPackage;
    private javax.swing.JButton buttonSearchProcedures;
    private javax.swing.JComboBox comboConnections;
    private javax.swing.JButton connectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JList listPackages;
    private javax.swing.JList listProcedures;
    private javax.swing.JButton newConnectionButton;
    private javax.swing.JTextField txtSearchPackages;
    private javax.swing.JTextField txtSearchProcedures;
    // End of variables declaration//GEN-END:variables
}
