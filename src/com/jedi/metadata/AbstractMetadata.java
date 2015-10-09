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
 * @author umit
 */
public abstract class AbstractMetadata implements Metadata {

    private List<Metadata> children;
    private long id;
    private String name;

    @Override
    public List<Metadata> getChildren() {
        return children;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addChild(Metadata child) {
        if (children == null) {
            children = new ArrayList<Metadata>();
        }

        children.add(child);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
