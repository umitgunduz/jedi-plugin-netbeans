/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.metadata;

/**
 *
 * @author EXT0104423
 */
public class CustomTypeArgumentInfo {
    private String owner;
    private String name;
    private String dataType;
    private String dataTypeOwner;
    private boolean isCustomObject;
    private int length; 
    private int precision;
    private int scale;
    private int position;
    
    private String fieldName;
    private String fieldType;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeOwner() {
        return dataTypeOwner;
    }

    public void setDataTypeOwner(String dataTypeOwner) {
        this.dataTypeOwner = dataTypeOwner;
    }

    public boolean isIsCustomObject() {
        return isCustomObject;
    }

    public void setIsCustomObject(boolean isCustomObject) {
        this.isCustomObject = isCustomObject;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    

    
}
