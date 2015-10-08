/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.metadata;

import java.util.List;

/**
 *
 * @author umit
 */
public class ProcedureMetadata extends AbstractMetadata{
    private String procedureCatalog;
    private String procedureSchema;
    private String procedureType;
    private List<ColumnMetadata> columns;

    /**
     * @return the procedureCatalog
     */
    public String getProcedureCatalog() {
        return procedureCatalog;
    }

    /**
     * @param procedureCatalog the procedureCatalog to set
     */
    public void setProcedureCatalog(String procedureCatalog) {
        this.procedureCatalog = procedureCatalog;
    }

    /**
     * @return the procedureSchema
     */
    public String getProcedureSchema() {
        return procedureSchema;
    }

    /**
     * @param procedureSchema the procedureSchema to set
     */
    public void setProcedureSchema(String procedureSchema) {
        this.procedureSchema = procedureSchema;
    }

    /**
     * @return the procedureType
     */
    public String getProcedureType() {
        return procedureType;
    }

    /**
     * @param procedureType the procedureType to set
     */
    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    /**
     * @return the columns
     */
    public List<ColumnMetadata> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<ColumnMetadata> columns) {
        this.columns = columns;
    }

}
