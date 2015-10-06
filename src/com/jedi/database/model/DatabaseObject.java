/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.database.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author umit
 */
public class DatabaseObject {

    private List<DatabaseObject> children;
    private String name;

    public List<DatabaseObject> getChildren() {
        return children;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addChild(DatabaseObject child) {
        if (children == null) {
            children = new ArrayList<DatabaseObject>();
        }

        children.add(child);
    }
}
