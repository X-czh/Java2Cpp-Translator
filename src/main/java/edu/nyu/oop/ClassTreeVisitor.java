package edu.nyu.oop;
import com.sun.deploy.association.utility.GnomeAssociationUtil;
import org.slf4j.Logger;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class ClassTreeVisitor extends Visitor {
    HashMap<String, TreeNode> tree_map = new HashMap<String, TreeNode>();
    TreeNode current_class;

    public void visit(Node n) {
        for (Object o : n) if (o instanceof Node) dispatch((Node) o);
    }

    public void visitClassDeclaration(GNode n) {
        current_class = new TreeNode();
        current_class.class_name = n.getString(1);
        Node extension = n.getNode(3);
        if (extension == null) current_class.parent_class_name = "Object";
        else current_class.parent_class_name = extension.getNode(0).getNode(0).getString(0);
        visit(n);
        tree_map.put(current_class.class_name, current_class);
    }

    public void visitFieldDeclaration(GNode n){
        current_class.field_list.add(n);
        visit(n);
    }

    public void visitMethodDeclaration(GNode n){
        current_class.method_list.add(n);
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n){
        current_class.constructor_list.add(n);
        visit(n);
    }

    public HashMap<String, TreeNode> getClassTree(List<GNode> javaAstList) {
        for (Node tree: javaAstList) super.dispatch(tree);
        return tree_map;
    }

    class TreeNode{
        String parent_class_name;
        ArrayList<GNode> field_list;
        ArrayList<GNode> method_list;
        ArrayList<GNode> constructor_list;
        String class_name;
    }

}
