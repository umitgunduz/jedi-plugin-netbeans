/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.metadata;

import java.util.List;
import java.util.Map;
import org.netbeans.api.db.explorer.DatabaseConnection;

/**
 *
 * @author umit
 */
public class ProcedureMetadata extends AbstractMetadata{
    private long packageId;
    private String packageName;
    private String schemaName;
    private String procedureType;
    private List<ArgumentMetadata> arguments;
    private DatabaseConnection databaseConnection;
    private Map<String,CustomTypeInfo> customTypes;

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    public List<ArgumentMetadata> getArguments() {
        return arguments;
    }

    public void setArguments(List<ArgumentMetadata> arguments) {
        this.arguments = arguments;
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Map<String, CustomTypeInfo> getCustomTypes() {
        return customTypes;
    }

    public void setCustomTypes(Map<String, CustomTypeInfo> customTypes) {
        this.customTypes = customTypes;
    }

    

    

    
}
