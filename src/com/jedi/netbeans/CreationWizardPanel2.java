/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.netbeans;

import com.jedi.metadata.ProcedureMetadata;
import java.sql.SQLException;
import javax.swing.event.ChangeListener;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;

public class CreationWizardPanel2 implements WizardDescriptor.Panel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private CreationVisualPanel2 component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public CreationVisualPanel2 getComponent() {
        if (component == null) {
            component = new CreationVisualPanel2();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {

        // use wiz.getProperty to retrieve previous panel state
        DatabaseConnection databaseConnection = (DatabaseConnection) wiz.getProperty("DatabaseConnection");
        this.getComponent().setDatabaseConnection(databaseConnection);

        ProcedureMetadata procedureMetadata = (ProcedureMetadata) wiz.getProperty("Procedure");
        this.getComponent().setProcedure(procedureMetadata);
        try {
            this.getComponent().loadArguments();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
    }

}
