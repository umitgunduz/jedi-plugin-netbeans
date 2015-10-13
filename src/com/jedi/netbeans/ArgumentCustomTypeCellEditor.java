/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

import com.jedi.metadata.ArgumentMetadata;
import com.jedi.metadata.ProcedureMetadata;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author EXT0104423
 */
public class ArgumentCustomTypeCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        ArgumentMetadataTableModel model = (ArgumentMetadataTableModel) table.getModel();
        ProcedureMetadata procedure = model.getProcedure();
        ArgumentMetadata argument = model.getArgument(row);

        JButton button = new JButton();
        CustomTypeButtonModel buttonModel = new CustomTypeButtonModel();
        buttonModel.setProcedure(procedure);
        buttonModel.setArgument(argument);
        button.setModel(buttonModel);
        button.setText("...");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                CustomTypeButtonModel buttonModel = (CustomTypeButtonModel) button.getModel();
                CustomTypeMappingWizardIterator wizardIterator = new CustomTypeMappingWizardIterator();
                wizardIterator.run(buttonModel.getProcedure(), buttonModel.getArgument()).actionPerformed(e);

            }
        });
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
