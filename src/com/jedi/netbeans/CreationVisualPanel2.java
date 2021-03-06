/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

import com.jedi.metadata.ArgumentMetadata;
import com.jedi.metadata.DatabaseMetadataUtil;
import com.jedi.metadata.ProcedureMetadata;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.TableColumn;
import org.netbeans.api.db.explorer.DatabaseConnection;

public final class CreationVisualPanel2 extends JPanel {

    private ProcedureMetadata procedure;

    public void setProcedure(ProcedureMetadata procedure) {
        this.procedure = procedure;
    }

    public ProcedureMetadata getProcedure() {
        return procedure;
    }

    /**
     * Creates new form CreationVisualPanel2
     */
    public CreationVisualPanel2() {
        initComponents();

    }

    public void loadArguments() throws SQLException {

        if (this.procedure == null) {
            return;
        }

        DatabaseConnection databaseConnection = procedure.getDatabaseConnection();
        if (databaseConnection == null) {
            return;
        }

        Connection connection = databaseConnection.getJDBCConnection();
        List<ArgumentMetadata> arguments = DatabaseMetadataUtil.getProcedureArguments(connection, this.procedure);
        procedure.setArguments(arguments);
        ArgumentMetadataTableModel model = new ArgumentMetadataTableModel(procedure);
        tableArguments.setModel(model);
        TableColumn fieldTypeColumn = tableArguments.getColumnModel().getColumn(4);
        String[] fieldTypes = {"String", "int", "long", "short", "float", "double", "BigDecimal", "byte", "byte[]", "boolean", "Date", "sql.Date", "sql.Time", "sql.Timestamp"};
        fieldTypeColumn.setCellEditor(new ArgumentFieldTypeCellEditor(fieldTypes));
        tableArguments.setRowHeight(25);
    }

    @Override
    public String getName() {
        return "Argument Type Mappings";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableArguments = new javax.swing.JTable();

        setPreferredSize(null);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CreationVisualPanel2.class, "CreationVisualPanel2.jLabel1.text")); // NOI18N

        tableArguments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableArguments);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableArguments;
    // End of variables declaration//GEN-END:variables
}
