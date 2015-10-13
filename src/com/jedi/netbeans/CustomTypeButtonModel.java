/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

import com.jedi.metadata.ArgumentMetadata;
import com.jedi.metadata.ProcedureMetadata;
import javax.swing.DefaultButtonModel;

/**
 *
 * @author EXT0104423
 */
public class CustomTypeButtonModel extends DefaultButtonModel{
    private ProcedureMetadata procedure;
    private ArgumentMetadata argument;

    public ProcedureMetadata getProcedure() {
        return procedure;
    }

    public void setProcedure(ProcedureMetadata procedure) {
        this.procedure = procedure;
    }

    public ArgumentMetadata getArgument() {
        return argument;
    }

    public void setArgument(ArgumentMetadata argument) {
        this.argument = argument;
    }
}
