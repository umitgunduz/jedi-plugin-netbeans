/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.metadata;

import com.google.common.base.CaseFormat;
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

    public static List<ArgumentMetadata> getProcedureArguments(Connection connection, ProcedureMetadata procedureMetadata) throws SQLException {
        List<ArgumentMetadata> result = new ArrayList<ArgumentMetadata>();
        String sql = "SELECT ARGUMENT_NAME,"
                + "       POSITION,"
                + "       SEQUENCE,"
                + "       DATA_TYPE,"
                + "       IN_OUT,"
                + "       DATA_LENGTH,"
                + "       DATA_PRECISION,"
                + "       DATA_SCALE,"
                + "       RADIX,"
                + "       TYPE_OWNER,"
                + "       TYPE_NAME,"
                + "       PLS_TYPE"
                + "  FROM ALL_ARGUMENTS"
                + " WHERE OBJECT_ID = " + procedureMetadata.getPackageId() + " AND SUBPROGRAM_ID = " + procedureMetadata.getId() + " AND DATA_LEVEL=0";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            ArgumentMetadata argumentMetadata = new ArgumentMetadata();
            String argumentName = resultSet.getString("ARGUMENT_NAME");
            argumentMetadata.setName(argumentName);
            argumentMetadata.setPosition(resultSet.getInt("POSITION"));
            argumentMetadata.setSequence(resultSet.getInt("SEQUENCE"));
            argumentMetadata.setDataType(resultSet.getString("DATA_TYPE"));
            argumentMetadata.setInOut(resultSet.getString("IN_OUT"));
            argumentMetadata.setLength(resultSet.getInt("DATA_LENGTH"));
            argumentMetadata.setPrecision(resultSet.getInt("DATA_PRECISION"));
            argumentMetadata.setScale(resultSet.getInt("DATA_SCALE"));
            argumentMetadata.setRadix(resultSet.getInt("RADIX"));
            argumentMetadata.setCustomTypeOwner(resultSet.getString("TYPE_OWNER"));
            argumentMetadata.setCustomTypeName(resultSet.getString("TYPE_NAME"));
            argumentMetadata.setPlsType(resultSet.getString("PLS_TYPE"));

            if (argumentName != null && !argumentName.isEmpty()) {
                argumentName = argumentName.toUpperCase();
                if (argumentName.startsWith("P_")) {
                    argumentName = argumentName.substring(2);
                }
                String fieldName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, argumentName);
                argumentMetadata.setFieldName(fieldName);
            } else {
                String fieldName = "result";
                argumentMetadata.setFieldName(fieldName);
            }

            result.add(argumentMetadata);
        }
        return result;

    }

    public static String getJavaType(String dataType) {
        switch (dataType) {
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
                return "String";
            case "NUMERIC":
            case "DECIMAL":
                return "BigDecimal";
            case "BIT":
                return "boolean";
            case "TINYINT":
                return "byte";
            case "SMALLINT":
                return "short";
            case "INTEGER":
                return "int";
            case "BIGINT":
                return "long";
            case "REAL":
                return "float";
            case "FLOAT":
            case "DOUBLE":
                return "double";
            case "BINARY":
            case "VARBINARY":
            case "LONGVARBINARY":
                return "byte[]";
            case "DATE":
            case "TIME":
            case "TIMESTAMP":
                return "Date";
            case "BLOB":
                return "byte[]";
            case "CLOB":
                return "String";
            default:
                return "";

        }
    }
}
