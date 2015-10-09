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

    public static List<PackageMetadata> searchPackages(String pattern, Connection connection) throws SQLException {
        List<PackageMetadata> result = new ArrayList<PackageMetadata>();
        pattern = pattern.toUpperCase();
        String sql = "SELECT OBJECT_ID,OBJECT_NAME FROM USER_OBJECTS WHERE OBJECT_TYPE='PACKAGE' AND STATUS='VALID' AND OBJECT_NAME LIKE '" + pattern + "%' ORDER BY OBJECT_NAME";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            long objectId = resultSet.getLong("OBJECT_ID");
            String packageName = resultSet.getString("OBJECT_NAME");
            PackageMetadata packageMetadata = new PackageMetadata();
            packageMetadata.setId(objectId);
            packageMetadata.setName(packageName);
            result.add(packageMetadata);
        }
        return result;
    }

    public static List<ProcedureMetadata> searchProcedures(String pattern, Connection connection, PackageMetadata packageMetadata) throws SQLException {
        List<ProcedureMetadata> result = new ArrayList<ProcedureMetadata>();
        pattern = pattern.toUpperCase();
        String sql = "  SELECT PROCEDURE_NAME,"
                + "         OBJECT_ID,"
                + "         SUBPROGRAM_ID,"
                + "         (SELECT COUNT (DATA_TYPE)"
                + "            FROM ALL_ARGUMENTS"
                + "           WHERE     OBJECT_ID = P.OBJECT_ID"
                + "                 AND DATA_TYPE IS NOT NULL"
                + "                 AND SUBPROGRAM_ID = P.SUBPROGRAM_ID"
                + "                 AND POSITION = 0"
                + "                 AND ARGUMENT_NAME IS NULL)"
                + "            IS_FUNCTION"
                + "    FROM USER_PROCEDURES P"
                + "   WHERE OBJECT_ID = " + packageMetadata.getId();
        if (pattern != null && !pattern.isEmpty()) {
            sql += " AND PROCEDURE_NAME LIKE '" + pattern.toUpperCase() + "%'";
        }
        sql += "ORDER BY PROCEDURE_NAME";
        DatabaseMetaData meta = connection.getMetaData();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String name = resultSet.getString("PROCEDURE_NAME");
            long objectId = resultSet.getLong("OBJECT_ID");
            int subProgramId = resultSet.getInt("SUBPROGRAM_ID");
            String procedureType = resultSet.getInt("IS_FUNCTION") == 1 ? "FUNCTION" : "PROCEDURE";

            ProcedureMetadata procedureMetadata = new ProcedureMetadata();
            procedureMetadata.setId(subProgramId);
            procedureMetadata.setName(name);
            procedureMetadata.setPackageId(objectId);
            procedureMetadata.setPackageName(packageMetadata.getName());
            procedureMetadata.setSchemaName(meta.getUserName());
            procedureMetadata.setProcedureType(procedureType);
            
            result.add(procedureMetadata);
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
