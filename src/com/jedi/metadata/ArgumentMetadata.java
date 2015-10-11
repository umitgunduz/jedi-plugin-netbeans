/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.metadata;

/**
 *
 * @author umit
 */
public class ArgumentMetadata extends AbstractMetadata{
    private String dataType;
    private int precision;
    private int length;
    private int scale;
    private int radix;
    private int position;
    private int sequence;
    private String inOut;
    private String customTypeOwner;
    private String customTypeName;
    private String plsType;
    
    private String fieldName;
    private String fieldType;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getRadix() {
        return radix;
    }

    public void setRadix(int radix) {
        this.radix = radix;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }

    public String getCustomTypeOwner() {
        return customTypeOwner;
    }

    public void setCustomTypeOwner(String customTypeOwner) {
        this.customTypeOwner = customTypeOwner;
    }

    public String getCustomTypeName() {
        return customTypeName;
    }

    public void setCustomTypeName(String customTypeName) {
        this.customTypeName = customTypeName;
    }

    public String getPlsType() {
        return plsType;
    }

    public void setPlsType(String plsType) {
        this.plsType = plsType;
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
