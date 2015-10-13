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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.java.project.support.ui.templates.JavaTemplates;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;

public final class CustomTypeMappingWizardIterator implements WizardDescriptor.Iterator<WizardDescriptor> {

    // Example of invoking this wizard:
    // @ActionID(category="...", id="...")
    // @ActionRegistration(displayName="...")
    // @ActionReference(path="Menu/...")
    public ActionListener run(final ProcedureMetadata procedure, final ArgumentMetadata argument) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomTypeMappingWizardIterator iterator = new CustomTypeMappingWizardIterator();
                WizardDescriptor wiz = new WizardDescriptor(iterator);
                // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
                // {1} will be replaced by WizardDescriptor.Iterator.name()
                //wiz.setTitleFormat(new MessageFormat("{0} ({1})"));
                wiz.setTitle("Custom Type Mapping");
                wiz.putProperty("Procedure", procedure);
                wiz.putProperty("Argument", argument);

//                Project p = Templates.getProject(wiz);
//                Sources sources = ProjectUtils.getSources(p);
//                SourceGroup sg[] = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
                
//                iterator.setProject(project);
//                iterator.setSources(sources);
//                iterator.setSourceGroups(sourceGroups);

                if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
                    //...do something...
                }
            }
        };
    }

    private int index;
    
    private Project project;
    private Sources sources;
    private SourceGroup sourceGroups[];

    public void setProject(Project project) {
        this.project = project;
    }

    public void setSources(Sources sources) {
        this.sources = sources;
    }

    public void setSourceGroups(SourceGroup[] sourceGroups) {
        this.sourceGroups = sourceGroups;
    }

    private List<WizardDescriptor.Panel<WizardDescriptor>> panels;

    private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {

        if (panels == null) {
            panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
            panels.add(new CustomTypeMappingWizardPanel1());
            panels.add(new CustomTypeMappingWizardPanel2());
            String[] steps = new String[panels.size()];
            for (int i = 0; i < panels.size(); i++) {
                Component c = panels.get(i).getComponent();
                // Default step name to component name of panel.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
                }
            }
        }
        return panels;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanels().get(index);
    }

    @Override
    public String name() {
        return index + 1 + ". from " + getPanels().size();
    }

    @Override
    public boolean hasNext() {
        return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then use
    // ChangeSupport to implement add/removeChangeListener and call fireChange
    // when needed
}
