package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class ClassTreeVisitor extends Visitor {
    HashMap<String,  ClassSignature> tree_map = new HashMap<String, ClassSignature>();
    ClassSignature current_class;

    public ClassTreeVisitor(List<GNode> astList) {
        // prepopulate Object, String and Class
        tree_map.put("Object", ClassSignature.buildObject());
        tree_map.put("String", ClassSignature.buildString());
        tree_map.put("Class", ClassSignature.buildClass());

        // traverse the whole AST forest
        for (Node n : astList)
            visit(n);
    }

    public void visit(Node n) {
        for (Object o : n) if (o instanceof Node) dispatch((Node) o);
    }

    public void visitClassDeclaration(GNode n) {
        String class_name, parent_class_name;
        class_name = n.getString(1);
        Node extension = n.getNode(3);
        if (extension == null) parent_class_name = "Object";
        else parent_class_name = extension.getNode(0).getNode(0).getString(0);
        visit(n);
        current_class = new ClassSignature(class_name, parent_class_name);
        tree_map.put(class_name, current_class);
    }

    public void visitFieldDeclaration(GNode n){
        current_class.addField(n);
        visit(n);
    }

    public void visitMethodDeclaration(GNode n){
        current_class.addMethod(n);
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n){
        current_class.addConstructor(n);
        visit(n);
    }

    public HashMap<String,  ClassSignature> getClassTree(List<GNode> javaAstList) {
        for (Node tree: javaAstList) super.dispatch(tree);
        return tree_map;
    }

}
