/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

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
        JButton button=new JButton();
        button.setText("...");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArgumentCustomTypeWizardAction action=new ArgumentCustomTypeWizardAction();
                action.actionPerformed(e);
            }
        });
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
