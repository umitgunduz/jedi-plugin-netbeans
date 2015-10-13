/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EXT0104423
 */
public class CustomTypeInfo {

    private String name;
    private String owner;
    private boolean isCollection;
    private String objectType;
    private String collectionType;
    private String collectionElementType;
    private String collectionElementTypeOwner;
    private List<CustomTypeArgumentInfo> arguments;

    private String javaClassName;
    private String javaPackageName;

    public CustomTypeInfo() {
        arguments = new ArrayList<CustomTypeArgumentInfo>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isIsCollection() {
        return isCollection;
    }

    public void setIsCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getCollectionElementType() {
        return collectionElementType;
    }

    public void setCollectionElementType(String collectionElementType) {
        this.collectionElementType = collectionElementType;
    }

    public String getCollectionElementTypeOwner() {
        return collectionElementTypeOwner;
    }

    public void setCollectionElementTypeOwner(String collectionElementTypeOwner) {
        this.collectionElementTypeOwner = collectionElementTypeOwner;
    }

    public List<CustomTypeArgumentInfo> getArguments() {
        return arguments;
    }

    public void setArguments(List<CustomTypeArgumentInfo> arguments) {
        this.arguments = arguments;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    public String getJavaPackageName() {
        return javaPackageName;
    }

    public void setJavaPackageName(String javaPackageName) {
        this.javaPackageName = javaPackageName;
    }

}
