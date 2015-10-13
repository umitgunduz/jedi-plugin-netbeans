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
            String datatype = resultSet.getString("DATA_TYPE");
            argumentMetadata.setDataType(datatype);
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

            String javaType = getJavaType(datatype);
            argumentMetadata.setFieldType(javaType);

            result.add(argumentMetadata);
        }
        return result;

    }

    public static String getJavaType(String dataType) {
        switch (dataType) {
            case "CHAR":
            case "VARCHAR2":
            case "LONG":
            case "CLOB":
            case "NCLOB":
            case "NCHAR":
                return "String";
            case "NUMBER":
                return "int";
            case "RAW":
            case "LONGRAW":
            case "BLOB":
            case "BFILE":
                return "byte[]";
            case "DATE":
            case "TIMESTAMP":
                return "Date";
            default:
                return "";

        }
    }

    public static CustomTypeInfo getCustomType(Connection connection, ArgumentMetadata argument) throws SQLException {
        String owner = argument.getCustomTypeOwner();
        String typeName = argument.getCustomTypeName();
        CustomTypeInfo customTypeInfo = null;
        String sql = "SELECT OWNER,"
                + "       TYPE_NAME,"
                + "       TYPECODE,"
                + "       TYPE_OID"
                + "  FROM ALL_TYPES"
                + " WHERE OWNER = '" + owner + "' AND TYPE_NAME = '" + typeName + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        customTypeInfo = new CustomTypeInfo();
        customTypeInfo.setOwner(resultSet.getString("OWNER"));
        customTypeInfo.setName(resultSet.getString("TYPE_NAME"));
        String objectType = resultSet.getString("TYPECODE");
        customTypeInfo.setObjectType(objectType);
        customTypeInfo.setIsCollection("COLLECTION".equals(objectType));
        if (customTypeInfo.isIsCollection()) {
            sql = "SELECT COLL_TYPE, ELEM_TYPE_OWNER, ELEM_TYPE_NAME"
                    + "  FROM ALL_COLL_TYPES"
                    + " WHERE ELEM_TYPE_OWNER = '" + owner + "'"
                    + "       AND TYPE_NAME = '" + typeName + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            customTypeInfo.setCollectionType(resultSet.getString("COLL_TYPE"));
            typeName = resultSet.getString("ELEM_TYPE_NAME");
            customTypeInfo.setCollectionElementType(typeName);
            owner = resultSet.getString("ELEM_TYPE_OWNER");
            customTypeInfo.setCollectionElementTypeOwner(owner);
        }

        sql = "SELECT OWNER,"
                + "         ATTR_NAME,"
                + "         ATTR_TYPE_OWNER,"
                + "         ATTR_TYPE_NAME,"
                + "         LENGTH,"
                + "         PRECISION,"
                + "         SCALE,"
                + "         ATTR_NO"
                + "    FROM ALL_TYPE_ATTRS"
                + "   WHERE OWNER = '" + owner + "' AND TYPE_NAME = '" + typeName + "'"
                + "ORDER BY ATTR_NO";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            CustomTypeArgumentInfo customTypeArgumentInfo = new CustomTypeArgumentInfo();
            String argumentName = resultSet.getString("ATTR_NAME");
            customTypeArgumentInfo.setName(argumentName);
            customTypeArgumentInfo.setOwner(resultSet.getString("OWNER"));
            String dataType = resultSet.getString("ATTR_TYPE_NAME");
            if (dataType != null && !dataType.isEmpty()) {
                dataType = dataType.toUpperCase();
                customTypeArgumentInfo.setDataType(dataType);
            }

            String dataTypeOwner = resultSet.getString("ATTR_TYPE_OWNER");
            customTypeArgumentInfo.setDataTypeOwner(dataTypeOwner);
            customTypeArgumentInfo.setPosition(resultSet.getInt("ATTR_NO"));
            customTypeArgumentInfo.setLength(resultSet.getInt("LENGTH"));
            customTypeArgumentInfo.setPrecision(resultSet.getInt("PRECISION"));
            customTypeArgumentInfo.setScale(resultSet.getInt("SCALE"));

            if (dataTypeOwner != null && !dataTypeOwner.isEmpty()) {
                customTypeArgumentInfo.setIsCustomObject(true);
            }

            customTypeInfo.getArguments().add(customTypeArgumentInfo);

            if (argumentName != null && !argumentName.isEmpty()) {
                argumentName = argumentName.toUpperCase();
                if (argumentName.startsWith("P_")) {
                    argumentName = argumentName.substring(2);
                }
                String fieldName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, argumentName);
                customTypeArgumentInfo.setFieldName(fieldName);

                String javaType = getJavaType(dataType);
                customTypeArgumentInfo.setFieldType(javaType);

            }
        }
        
        return customTypeInfo;
    }
}
