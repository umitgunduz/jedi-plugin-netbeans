/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author umit
 */
public class DatabaseMetadataUtil {

    public static List<PackageMetadata> getPackages(Connection connection) throws SQLException {
        List<PackageMetadata> result = new ArrayList<PackageMetadata>();
        String sql = "select object_name from user_objects where object_type='PACKAGE' and status='VALID'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String packageName = resultSet.getString("object_name");
            PackageMetadata packageMetadata = new PackageMetadata();
            packageMetadata.setName(packageName);
            result.add(packageMetadata);
        }
        return result;
    }

    public static List<ProcedureMetadata> getProcedures(Connection connection) throws SQLException {
        return getProcedures(connection, null);
    }

    public static List<ProcedureMetadata> getProcedures(Connection connection, PackageMetadata packageMetadata) throws SQLException {
        List<ProcedureMetadata> result = new ArrayList<ProcedureMetadata>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String catalog = "%";
        if (packageMetadata != null && !packageMetadata.getName().isEmpty()) {
            catalog = packageMetadata.getName();
        } else {
            catalog = connection.getCatalog();
        }
        ResultSet resultSet = databaseMetaData.getProcedures(catalog, databaseMetaData.getUserName(), "%");
        while (resultSet.next()) {
            int procedureType = resultSet.getInt("PROCEDURE_TYPE");
            if (procedureType == 1) {
                ProcedureMetadata storedProcedureMetadata = new ProcedureMetadata();
                storedProcedureMetadata.setName(resultSet.getString("PROCEDURE_NAME"));
                storedProcedureMetadata.setProcedureCatalog(resultSet.getString("PROCEDURE_CAT"));
                storedProcedureMetadata.setProcedureSchema(resultSet.getString("PROCEDURE_SCHEM"));
                storedProcedureMetadata.setProcedureType(resultSet.getString("REMARKS"));
                result.add(storedProcedureMetadata);
            }
        }
        return result;
    }

    public static List<ColumnMetadata> getProcedureColumns(Connection connection, String procedureName) throws SQLException {
        List<ColumnMetadata> result = new ArrayList<ColumnMetadata>();
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getProcedureColumns(connection.getCatalog(), meta.getUserName(), procedureName, "%");
        while (resultSet.next()) {
            ColumnMetadata columnMetadata = new ColumnMetadata();
            columnMetadata.setName(resultSet.getString("COLUMN_NAME"));
            columnMetadata.setColumnType(resultSet.getInt("COLUMN_TYPE"));
            columnMetadata.setDataType(resultSet.getInt("DATA_TYPE"));
            columnMetadata.setTypeName(resultSet.getString("TYPE_NAME"));
            columnMetadata.setPrecision(resultSet.getInt("PRECISION"));
            columnMetadata.setLength(resultSet.getInt("LENGTH"));
            columnMetadata.setScale(resultSet.getInt("SCALE"));
            columnMetadata.setRadix(resultSet.getInt("RADIX"));
            columnMetadata.setNullable(resultSet.getInt("NULLABLE") == 1);
            columnMetadata.setRemarks(resultSet.getString("REMARKS"));
            columnMetadata.setOrdinalPosition(resultSet.getInt("ORDINAL_POSITION"));
            columnMetadata.setSequence(resultSet.getInt("SEQUENCE"));
            result.add(columnMetadata);
        }
        return result;
    }
}
