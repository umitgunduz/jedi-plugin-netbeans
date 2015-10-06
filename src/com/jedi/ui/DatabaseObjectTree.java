/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jedi.ui;

import com.jedi.database.model.DatabaseObject;
import java.awt.Component;
import java.util.List;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author umit
 */
public class DatabaseObjectTree extends JTree {

    public DatabaseObjectTree(){
        setCellRenderer(new DatabaseObjectTreeCellRenderer(getCellRenderer()));
    }
    
    public static class DatabaseObjectTreeModel implements TreeModel {

        protected DatabaseObject root;

        public DatabaseObjectTreeModel(DatabaseObject root) {
            this.root = root;
        }

        @Override
        public Object getRoot() {
            return root;
        }

        @Override
        public Object getChild(Object parent, int index) {
            List<DatabaseObject> children = ((DatabaseObject) parent).getChildren();
            if ((children == null) || (index >= children.size())) {
                return null;
            }
            return children.get(index);
        }

        @Override
        public int getChildCount(Object parent) {
            List<DatabaseObject> children = ((DatabaseObject) parent).getChildren();
            if ((children == null)) {
                return 0;
            }
            return children.size();
        }

        @Override
        public boolean isLeaf(Object node) {
            List<DatabaseObject> children = ((DatabaseObject) node).getChildren();
            return children == null || children.isEmpty();
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {

        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            List<DatabaseObject> children = ((DatabaseObject) parent).getChildren();
            if (children == null || children.isEmpty()) {
                return -1;
            }
            String childname = ((DatabaseObject) child).getName();
            for (int i = 0; i < children.size(); i++) {
                if (childname.equals(children.get(i).getName())) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {

        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {

        }

    }

    static class DatabaseObjectTreeCellRenderer implements TreeCellRenderer {

        TreeCellRenderer renderer;

        public DatabaseObjectTreeCellRenderer(TreeCellRenderer renderer) {
            this.renderer = renderer;
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            String name = ((DatabaseObject) value).getName(); 
            return renderer.getTreeCellRendererComponent(tree, name,
                    selected, expanded, leaf, row, hasFocus);
        }
    }
}
