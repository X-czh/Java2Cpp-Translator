package edu.nyu.oop;

import edu.nyu.oop.util.ChildToParentMap;
import edu.nyu.oop.util.NodeUtil;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mutator extends Visitor {
    private List<Node> mutatedCppAstList;
    private GNode currentCppAst;
    private GNode prevHierarchy;
    private String currentClassName;
    private List<String> packageInfo;
    private Map<String, ClassSignature> classTreeMap;
    private ChildToParentMap child_parent_map;

    public Mutator(Map<String, ClassSignature> map) {
        classTreeMap = map;
        currentClassName = "";
        mutatedCppAstList = new ArrayList<>();
    }

    public List<Node> mutate(List<Node> javaAstList) {
        for (Node tree: javaAstList) {
            currentCppAst = GNode.create("CompilationUnit()");
            mutatedCppAstList.add(currentCppAst);
            prevHierarchy = currentCppAst;
            child_parent_map = new ChildToParentMap(tree);
            super.dispatch(tree);
        }
        return javaAstList;
    }

    public void visitPackageDeclaration(GNode n){
        packageInfo = new ArrayList<>();
        Node temp = n.getNode(1);
        for (int i=0; i<temp.size(); i++) {
            packageInfo.add(temp.getString(i));
        }

        for (String s : packageInfo) {
            GNode namespace = GNode.create("NamespaceDeclaration");
            namespace.add(s);
            prevHierarchy.add(namespace);
            prevHierarchy = namespace;
        }

        prevHierarchy.add(makeDefaultConstructor());
        prevHierarchy.add(make__classMethod());
        prevHierarchy.add(makeVTableInitialization());
    }

    public void visitClassDecalaration(GNode n) {
        currentClassName = n.getString(1);
    }

    public void visitConstructorDeclaration(GNode n) {
        // mutate block
        Node block = n.getNode(5);
        //TODO

        // create initMethod GNode
        GNode initMethod = GNode.create("MethodDeclaration");
        initMethod.add(null); // modifiers
        initMethod.add(null);
        initMethod.add(GNode.create("VoidType")); // return type
        initMethod.add("__init"); // method name
        initMethod.add(n.getNode(3)); // parameters
        initMethod.add(null);
        initMethod.add(null);
        initMethod.add(block); // block

        prevHierarchy.add(initMethod);
    }

    public void visitMethodDeclaration(GNode n) {
        List<String> modifiers = new ArrayList<>();
        Node mods = NodeUtil.dfs(n, "Modifiers");
        for (Node mod : NodeUtil.dfsAll(mods, "Modifier"))
            modifiers.add(mod.getString(0));

        if (!modifiers.contains("static") && !modifiers.contains("private")) {
            n.getNode(4).add(0, makeExplicitThisParameter()); // add explicit this parameter
        }

        prevHierarchy.add(n);
    }

    public Node visitCallExpression(GNode n) {
        // check whether it is System.out.print/println ()
        if (n.getNode(0).getName().equals("SelectionExpression")) {
            if (n.getNode(0).getNode(0).getName().equals("PrimaryIdentifier")) {
                if (n.getNode(0).getNode(0).getString(0).equals("System")) {
                    if (n.getNode(0).getString(1).equals("out")) {
                        GNode printingExpression = GNode.create("PrintingExpression");
                        printingExpression.add(n.getNode(3).getNode(0));
                        printingExpression.add(n.getString(2));
                        return printingExpression;
                    }
                }
            }
        }
        return n;
    }

    public void visit(GNode n) {
        for (int i = 0; i < n.size(); ++i) {
            Object o = n.get(i);
            if (o instanceof Node) {
                Object o1 = dispatch((Node) o);
                if (o1 != null && o1 instanceof Node) {
                    n.set(i, o1);
                }
            }
        }
    }

    private GNode makeDefaultConstructor() {
        GNode constructor = GNode.create("ConstructorDeclaration");

        constructor.add(null); // modifiers
        constructor.add(null);
        constructor.add("__" + currentClassName +"::__" + currentClassName);
        constructor.add(GNode.create("FormalParameters"));

        GNode init_list = GNode.create("Initializations");
        GNode init = GNode.create("Initialize");
        init.add("__vptr");
        init.add("&__vtable");
        init_list.add(init);
        constructor.add(init_list);

        constructor.add(GNode.create("Block"));

        return constructor;
    }

    private GNode make__classMethod() {
        GNode classMethod = GNode.create("ClassMethodDefinition");

        String className = "";
        for (String s : packageInfo)
            className = className + s + ".";
        className += currentClassName;
        classMethod.add(className);
        classMethod.add(classTreeMap.get(currentClassName).getParentClassName());

        return classMethod;
    }

    private GNode makeVTableInitialization() {
        GNode vtableInit = GNode.create("FieldDeclaration");
        vtableInit.add(null); // modifiers

        GNode type = GNode.create("Type");
        GNode temp = GNode.create("QualifiedIdentifier");
        temp.add("__" + currentClassName + "_VT");
        type.add(temp); // type name
        type.add(null); // dimension
        vtableInit.add(type);

        GNode declarators = GNode.create("Declarators");
        GNode declarator = GNode.create("Declarator");
        declarator.add("__" + currentClassName + "::__vtable");
        declarator.add(null);
        declarator.add(null);
        declarators.add(declarator);
        vtableInit.add(declarators);

        return vtableInit;
    }

    private GNode makeExplicitThisParameter() {
        GNode explicitThisParam = GNode.create("FormalParameter");

        explicitThisParam.add(null); // modifiers

        GNode type = GNode.create("Type");
        GNode temp = GNode.create("QualifiedIdentifier");
        temp.add(currentClassName);
        type.add(temp); // type name
        type.add(null); // dimension
        explicitThisParam.add(type);

        explicitThisParam.add(null);
        explicitThisParam.add("__this"); // parameter name
        explicitThisParam.add(null);

        return explicitThisParam;
    }

}
