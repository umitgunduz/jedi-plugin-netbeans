/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

import com.jedi.metadata.ProcedureMetadata;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author EXT0104423
 */
public class ProcedureListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        if (value instanceof ProcedureMetadata) {
            ProcedureMetadata procedureMetadata = (ProcedureMetadata) value;
            Icon icon = null;
            if ("PROCEDURE".equals(procedureMetadata.getProcedureType())) {
                icon = new ImageIcon(getClass().getResource("procedure.png"));
            } else {
                icon = new ImageIcon(getClass().getResource("function.png"));
            }

            label.setIcon(icon);
            label.setHorizontalTextPosition(JLabel.RIGHT);
        }

        return label;
    }
}
