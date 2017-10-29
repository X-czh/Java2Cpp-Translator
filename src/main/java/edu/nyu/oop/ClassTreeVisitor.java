package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.lang.Class;


public class ClassTreeVisitor extends Visitor {
    private Map<String,  ClassSignature> tree_map;
    private ClassSignature current_class;
    private Class cls;

    public void visitClassDeclaration(GNode n) {
        String class_name, parent_class_name;
        class_name = n.getString(1);
        Node extension = n.getNode(3);
        if (extension == null)
            parent_class_name = "Object";
        else
            parent_class_name = extension.getNode(0).getNode(0).getString(0);
        current_class = new ClassSignature(class_name, parent_class_name);
        tree_map.put(class_name, current_class);
        visit(n);
    }

    public void visitFieldDeclaration(GNode n){
        FieldSignature();
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

    public String getPackageInfo(){
        Package pkg = cls.getPackage();
        return pkg.toString();
    }

    public void visit(Node n) {
        for (Object o : n) if (o instanceof Node) dispatch((Node) o);
    }

    public HashMap<String, ClassSignature> getClassTree(List<Node> javaAstList) {
        tree_map = new HashMap<>();

        // prepopulate Object, String and Class
        tree_map.put("Object", ClassSignature.buildObject());
        tree_map.put("String", ClassSignature.buildString());
        tree_map.put("Class", ClassSignature.buildClass());

        // traverse the whole AST forest
        for (Node tree: javaAstList)
            super.dispatch(tree);

        return tree_map;
    }

}
