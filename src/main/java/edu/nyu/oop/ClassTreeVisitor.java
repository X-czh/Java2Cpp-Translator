package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import edu.nyu.oop.util.RecursiveVisitor;
import xtc.tree.GNode;
import xtc.tree.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class ClassTreeVisitor extends RecursiveVisitor {
    private Map<String,  ClassSignature> tree_map;
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
        List<String> modifiers = new ArrayList<>();
        Node mods = NodeUtil.dfs(n, "Modifiers");
        for (Node mod : NodeUtil.dfsAll(mods, "Modifier"))
            modifiers.add(mod.getString(0));

        String type;
        Node tp = NodeUtil.dfs(n, "Type");

        List<String> declarators = new ArrayList<>();
        Node decs = NodeUtil.dfs(n, "Declarators");
        for (Node dec : NodeUtil.dfsAll(decs, "Declarator"))
            declarators.add(dec.getString(0));

        FieldSignature f = new FieldSignature(modifiers, tp, declarators);
        current_class.addField(f);

        visit(n);
    }

    public void visitMethodDeclaration(GNode n){
        List<String> modifiers = new ArrayList<>();
        Node mods = NodeUtil.dfs(n, "Modifiers");
        for(Node mod : NodeUtil.dfsAll(mods, "Modifier"))
            modifiers.add(mod.getString(0));

        Node return_type = n.getNode(2);
        if (return_type.size() == 0)
            return_type = ClassSignature.createType("void", null);

        String method_name;
        method_name = n.getString(3);

        List<String> parameters = new ArrayList<>();
        Node params = NodeUtil.dfs(n,"FormalParameters");
        for(Node param : NodeUtil.dfsAll(params,"FormalParameter"))
            parameters.add(param.getString(3));

        List<Node> parameter_types = new ArrayList<>();
        Node pts = NodeUtil.dfs(n, "FormalParameters");
        for(Node pt : NodeUtil.dfsAll(pts, "FormalParameter"))
            parameter_types.add(pt.getNode(1));

        MethodSignature m = new MethodSignature(modifiers, return_type, method_name,parameters,parameter_types);
        current_class.addMethod(m);

        visit(n);
    }

    public void visitConstructorDeclaration(GNode n){
        String name;
        Node nm = NodeUtil.dfs(n, n.getString(3));
        name = nm.getNode(0).getString(0);

        List<String> parameters = new ArrayList<>();
        Node params = NodeUtil.dfs(n,"FormalParameters");
        for(Node param : NodeUtil.dfsAll(params, "FormalParameter"))
            parameters.add(param.getString(0));

        List<Node> parameter_types = new ArrayList<>();
        Node pts = NodeUtil.dfs(n, "FormalParameters");
        for(Node pt : NodeUtil.dfsAll(pts, "FormalParameter"))
            parameter_types.add(pt.getNode(1));

        ConstructorSignature c = new ConstructorSignature(name,parameters,parameter_types);
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
        for (Node tree: javaAstList)
            super.dispatch(tree);

        return tree_map;
    }

}
