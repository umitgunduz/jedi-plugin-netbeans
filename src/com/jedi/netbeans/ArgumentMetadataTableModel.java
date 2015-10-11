/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

import com.jedi.metadata.ArgumentMetadata;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author EXT0104423
 */
public class ArgumentMetadataTableModel extends AbstractTableModel {

    private String[] columnNames = {"Argument Name", "Data Type", "Argument Type", "Field Name", "Field Type"};
    private List<ArgumentMetadata> data;

    public ArgumentMetadataTableModel(List<ArgumentMetadata> arguments) {
        this.data = arguments;
    }

    @Override
    public int getRowCount() {
        int result = 0;
        if (data != null && !data.isEmpty()) {
            result = data.size();
        }
        return result;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArgumentMetadata argument = data.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = argument.getName();
                break;
            case 1:
                value = argument.getDataType();
                break;
            case 2:
                value = argument.getInOut();
                break;
            case 3:
                value = argument.getFieldName();
                break;
            case 4:
                value = argument.getFieldType();
                break;
        }
        return value;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        boolean result = false;
        switch (columnIndex) {
            case 3:
            case 4:
                result = true;
                break;
        }

        return result;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ArgumentMetadata item = data.get(rowIndex);
        switch (columnIndex) {
            case 3:
                item.setFieldName((String) aValue);
                break;
            case 4:
                item.setFieldType((String) aValue);
                break;
        }
        data.set(rowIndex, item);
    }

}
