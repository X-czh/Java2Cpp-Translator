package edu.nyu.oop;

import edu.nyu.oop.util.ChildToParentMap;
import edu.nyu.oop.util.NodeUtil;
import edu.nyu.oop.util.RecursiveVisitor;
import xtc.tree.GNode;
import xtc.tree.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * A ClassTreeVisitor visits a list of Java ASTs, and resolves the inheritance relationship
 * among the Java classes, with the class hierarchy info stored in a hash table. It also
 * parses the ASTs to generate the signature of each Java class, as well as the signatures
 * of each field, method, and constructor of the class.
 */
public class ClassTreeVisitor extends RecursiveVisitor {
    private Map<String,  ClassSignature> tree_map;
    private ChildToParentMap child_parent_map;
    private ClassSignature current_class;
    List<String> package_declaration = new ArrayList<>();

    public void visitClassDeclaration(GNode n) {
        String class_name, parent_class_name;
        class_name = n.getString(1);
        Node extension = n.getNode(3);
        if (extension == null)
            parent_class_name = "Object";
        else
            parent_class_name = extension.getNode(0).getNode(0).getString(0);
        current_class = new ClassSignature(class_name, parent_class_name);
        visit(n);
        tree_map.put(class_name, current_class);
    }

    public void visitFieldDeclaration(GNode n) {
        if (!child_parent_map.fetchParentFor(n).getName().equals("ClassBody"))
            return;

        List<String> modifiers = new ArrayList<>();
        Node mods = NodeUtil.dfs(n, "Modifiers");
        for (Node mod : NodeUtil.dfsAll(mods, "Modifier"))
            modifiers.add(mod.getString(0));

        Node tp = NodeUtil.dfs(n, "Type");
        String typeName = tp.getNode(0).getString(0);
        Node typeDimension = tp.getNode(1);
        tp = TypeResolver.createType(typeName, typeDimension);

        List<String> declarators = new ArrayList<>();
        Node decs = NodeUtil.dfs(n, "Declarators");
        for (Node dec : NodeUtil.dfsAll(decs, "Declarator"))
            declarators.add(dec.getString(0));

        FieldSignature f = new FieldSignature(modifiers, tp, declarators);
        current_class.addField(f);

        visit(n);
    }

    public void visitMethodDeclaration(GNode n){
        String method_name = n.getString(3);
        Node return_type = n.getNode(2);

        // check whether it is a constructor
        // ConstructorDeclaration get converted to MethodDeclaration automatically when building up the symbol table
        if (method_name.equals(current_class.getClassName()) && return_type == null) {
            extractConstructorSignature(n);
            visit(n);
            return;
        }

        List<String> modifiers = new ArrayList<>();
        Node mods = NodeUtil.dfs(n, "Modifiers");
        for (Node mod : NodeUtil.dfsAll(mods, "Modifier"))
            modifiers.add(mod.getString(0));

        if (return_type.size() == 0)
            return_type = TypeResolver.createType("void", null);
        else {
            String typeName = return_type.getNode(0).getString(0);
            Node typeDimension = return_type.getNode(1);
            return_type = TypeResolver.createType(typeName, typeDimension);
        }

        List<String> parameters = new ArrayList<>();
        Node params = NodeUtil.dfs(n,"FormalParameters");
        for (Node param : NodeUtil.dfsAll(params,"FormalParameter"))
            parameters.add(param.getString(3));

        List<Node> parameter_types = new ArrayList<>();
        Node pts = NodeUtil.dfs(n, "FormalParameters");
        for (Node pt : NodeUtil.dfsAll(pts, "FormalParameter")) {
            Node tp = pt.getNode(1);
            String typeName = tp.getNode(0).getString(0);
            Node typeDimension = tp.getNode(1);
            tp = TypeResolver.createType(typeName, typeDimension);
            parameter_types.add(tp);
        }

        // assume no other method named "main" though and do not support array typed parameters
        if (!method_name.equals("main")) {
            StringBuilder new_name = new StringBuilder(method_name);
            for (Node tp : parameter_types)
                new_name.append("_" + tp.toString());
            method_name = new_name.toString();
            n.set(3, method_name);
        }

        MethodSignature m = new MethodSignature(modifiers, return_type, method_name,
                parameters, parameter_types, n);
        current_class.addMethod(m);

        visit(n);
    }

    public void extractConstructorSignature(GNode n) {
        String name = n.getString(3);

        List<String> parameters = new ArrayList<>();
        Node params = NodeUtil.dfs(n, "FormalParameters");
        for (Node param : NodeUtil.dfsAll(params, "FormalParameter"))
            parameters.add(param.getString(3));

        List<Node> parameter_types = new ArrayList<>();
        Node pts = NodeUtil.dfs(n, "FormalParameters");
        for (Node pt : NodeUtil.dfsAll(pts, "FormalParameter")) {
            Node tp = pt.getNode(1);
            String typeName = tp.getNode(0).getString(0);
            Node typeDimension = tp.getNode(1);
            tp = TypeResolver.createType(typeName, typeDimension);
            parameter_types.add(tp);
        }

        ConstructorSignature c = new ConstructorSignature(name, parameters, parameter_types);
        current_class.addConstructor(c);

        visit(n);
    }

    public void visitPackageDeclaration(GNode n){
        Node temp = n.getNode(1);
        for (int i=0; i<temp.size(); i++) {
            package_declaration.add(temp.getString(i));
        }
    }

    public List<String> getPackageInfo(){
        return package_declaration;
    }

    public Map<String, ClassSignature> getClassTree(List<Node> javaAstList) {
        tree_map = new HashMap<>();

        // prepopulate Object, String and Class
        tree_map.put("Object", ClassSignature.buildObject());
        tree_map.put("String", ClassSignature.buildString());
        tree_map.put("Class", ClassSignature.buildClass());

        // traverse the whole AST forest
        for (Node tree: javaAstList) {
            child_parent_map = new ChildToParentMap(tree);
            super.dispatch(tree);
        }
        mangleMethodName();

        return tree_map;
    }

    private void mangleMethodName() {
        for (ClassSignature c : tree_map.values()) {
            for (MethodSignature m : c.getMethodList()) {
                String methodName = m.getMethodName();
                for (FieldSignature f : c.getFieldList()) {
                    for (String fieldName : f.getDeclarators()) {
                        if (fieldName.equals(methodName)) {
                            m.setMethodName(m.getMethodName() + "_impl");
                        }
                    }
                }
            }
        }
    }

}
