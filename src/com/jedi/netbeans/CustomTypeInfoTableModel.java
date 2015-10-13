/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

import com.jedi.metadata.CustomTypeArgumentInfo;
import com.jedi.metadata.CustomTypeInfo;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author EXT0104423
 */
public class CustomTypeInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Argument Name", "Data Type", "Field Name", "Field Type", ""};
    private final CustomTypeInfo customTypeInfo;

    public CustomTypeInfoTableModel(CustomTypeInfo customTypeInfo) {
        this.customTypeInfo = customTypeInfo;
    }

    @Override
    public int getRowCount() {
        int result = 0;
        if (customTypeInfo != null) {
            if (customTypeInfo.getArguments() != null && !customTypeInfo.getArguments().isEmpty()) {
                result = customTypeInfo.getArguments().size();
            }
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
        CustomTypeArgumentInfo argument = customTypeInfo.getArguments().get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = argument.getName();
                break;
            case 1:
                value = argument.getDataType();
                break;
            case 2:
                value = argument.getFieldName();
                break;
            case 3:
                value = argument.getFieldType();
                break;
        }
        return value;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        boolean result = false;
        String dataType = (String) this.getValueAt(rowIndex, 1);
        switch (columnIndex) {
            case 3:
                result = true;
            case 4:
                switch (dataType) {
                    case "OBJECT":
                    case "TABLE":
                    case "REF CURSOR":
                        break;
                    default:
                        result = true;
                        break;
                }
                break;
            case 5:
                switch (dataType) {
                    case "OBJECT":
                    case "TABLE":
                    case "REF CURSOR":
                        result = true;
                        break;
                }
                break;
        }

        return result;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (customTypeInfo == null || customTypeInfo.getArguments() == null || customTypeInfo.getArguments().isEmpty()) {
            return;
        }

        CustomTypeArgumentInfo item = customTypeInfo.getArguments().get(rowIndex);
        switch (columnIndex) {
            case 2:
                item.setFieldName((String) aValue);
                break;
            case 3:
                item.setFieldType((String) aValue);
                break;
        }

        customTypeInfo.getArguments().set(rowIndex, item);
    }

}
