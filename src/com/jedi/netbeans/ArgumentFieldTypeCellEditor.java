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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author EXT0104423
 */
public class ArgumentFieldTypeCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {

    private String fieldType;
    private final String[] fieldTypes;

    public ArgumentFieldTypeCellEditor(String[] fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    @Override
    public Object getCellEditorValue() {
        return this.fieldType;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.fieldType = (String) value;

        JComboBox<String> comboFieldTypes = new JComboBox<String>();
        AutoCompleteDecorator.decorate(comboFieldTypes);

        for (String item : fieldTypes) {
            comboFieldTypes.addItem(item);
        }

        comboFieldTypes.setSelectedItem(fieldType);
        comboFieldTypes.addActionListener(this);

        /*
         if (isSelected) {
         comboFieldTypes.setBackground(table.getSelectionBackground());
         } else {
         comboFieldTypes.setBackground(table.getSelectionForeground());
         }
         */
        return comboFieldTypes;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox<String> comboFieldTypes = (JComboBox<String>) event.getSource();
        this.fieldType = (String) comboFieldTypes.getSelectedItem();
    }

}
