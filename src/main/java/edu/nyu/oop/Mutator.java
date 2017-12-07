package edu.nyu.oop;

import edu.nyu.oop.util.ChildToParentMap;
import edu.nyu.oop.util.TypeUtil;
import xtc.lang.JavaEntities;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mutator extends Visitor {
    private GNode prevHierarchy;
    private String currentClassName;
    private String mainMethodClassName;
    private List<Node> classInitialization;
    private Map<String, ClassSignature> classTreeMap;
    private List<String> packageInfo;
    private ChildToParentMap childParentMap;

    public Mutator(Map<String, ClassSignature> classTreeMap, List<String> packageInfo) {
        this.classTreeMap = classTreeMap;
        this.packageInfo = packageInfo;
    }

    public Node mutate(List<Node> javaAstList) {
        GNode mutatedCppAst = GNode.create("CompilationUnit");
        prevHierarchy = mutatedCppAst;

        for (String s : packageInfo) {
            GNode namespace = GNode.create("NamespaceDeclaration");
            namespace.add(s);
            prevHierarchy.add(namespace);
            prevHierarchy = namespace;
        }

        for (Node tree : javaAstList) {
            classInitialization = new ArrayList<>();
            childParentMap = new ChildToParentMap(tree);
            super.dispatch(tree);
        }

        return mutatedCppAst;
    }

    public Node makeMainAst() {
        GNode mainAst = GNode.create("CompilationUnit");
        GNode mainMethod = GNode.create("MainMethodDefinition");
        String temp = "";
        for (String s : packageInfo)
            temp = temp + s + "::";
        temp = temp + "__" + mainMethodClassName;
        mainMethod.add(temp);
        mainAst.add(mainMethod);

        return mainAst;
    }

    public void visitClassDeclaration(GNode n) {
        currentClassName = n.getString(1);

        prevHierarchy.add(makeDefaultConstructor());
        prevHierarchy.add(make__classMethod());
        prevHierarchy.add(makeVTableInitialization());

        visit(n);
    }

    public void visitFieldDeclaration(GNode n) {
        visit(n);

        // check whether it take part in the class initializing process
        if (!childParentMap.fetchParentFor(n).getName().equals("ClassBody"))
            return;

        for (Object declarator : n.getNode(2)) {
            if (((Node) declarator).getNode(2) != null) {
                String var = ((Node) declarator).getString(0);
                Node value = ((Node) declarator).getNode(2);
                GNode n1 = GNode.create("SelectionExpression", makeThisExpression(), var);
                GNode n2 = GNode.create("Expression", n1, "=", value);
                GNode n3 = GNode.create("ExpressionStatement", n2);
                classInitialization.add(n3);
            }
        }
    }

    public void visitBlockDeclaration(GNode n) {
        visit(n);

        // part of the class initializing process, add to classInitialization
        for (int i = 0; i < n.size(); ++i)
            classInitialization.add(n.getNode(i));
    }

    public void mutateConstructorDeclaration(GNode n) {
        // mutate block
        boolean superFlag =false;
        boolean thisFlag =false;
        Node block = n.getNode(7);
        if (block.size() > 0) {
            Object o = block.get(0);
            if (o instanceof Node) {
                if (((Node) o).getName().equals("ExpressionStatement") &&
                        ((Node) o).getNode(0).getName().equals("CallExpression")) {
                    // mutate super/this call expression (must be the first expression)
                    Node callExpression = ((Node) o).getNode(0);
                    String callExpressionName = callExpression.getString(2);
                    if (callExpressionName.equals("super")) {
                        superFlag = true;
                        callExpression.set(2,
                                "__" + classTreeMap.get(currentClassName).getParentClassName() + "::__init");
                        callExpression.set(3, addExplicitThisArgument(callExpression.getNode(3)));
                    } else if (callExpressionName.equals("this")) {
                        thisFlag = true;
                        callExpression.set(2,
                                "__" + currentClassName + "::__init");
                        callExpression.set(3, addExplicitThisArgument(callExpression.getNode(3)));
                    }
                }
            }
        }
        GNode mutatedBlock = GNode.create("Block");
        if (superFlag || thisFlag) {
            // has call to other constructors either of this class or of the super class
            mutatedBlock.add(block.getNode(0));
            if (!thisFlag) {
                // no call to other constructors of this class
                for (Node t : classInitialization)
                    mutatedBlock.add(t);
            }
            for (int i = 1; i < block.size(); ++i)
                mutatedBlock.add(block.get(i));
        } else {
            // no call to other constructors either of this class or of the super class
            for (Node t : classInitialization)
                mutatedBlock.add(t);
            for (Object o : block)
                mutatedBlock.add(o);
        }

        // create initMethod GNode
        GNode initMethod = GNode.create("MethodDeclaration");
        initMethod.add(null); // modifiers
        initMethod.add(null);

        GNode type = GNode.create("Type");
        GNode temp_type = GNode.create("QualifiedIdentifier");
        temp_type.add(currentClassName);
        type.add(temp_type); // type name
        type.add(null); // dimension
        initMethod.add(type); // return type

        initMethod.add("__" + currentClassName + "::__init"); // method name

        initMethod.add(addExplicitThisParameter(n.getNode(4))); // parameters

        initMethod.add(null);
        initMethod.add(null);
        initMethod.add(mutatedBlock); // block

        prevHierarchy.add(initMethod);
    }

    public void visitMethodDeclaration(GNode n) {
        Object returnType = n.getNode(2);
        String methodName = n.getString(3);
        Type methodType = TypeUtil.getType(n);

        // check whether it is a constructor
        // ConstructorDeclaration get converted to MethodDeclaration automatically when building up the symbol table
        if (methodName.equals(currentClassName) && returnType == null) {
            mutateConstructorDeclaration(n);
            visit(n);
            return;
        }

        // mutate method starts
        // mutate parameter list
        if (!JavaEntities.hasModifier(methodType, "static") &&
                !JavaEntities.hasModifier(methodType, "private"))
            n.set(4, addExplicitThisParameter(n.getNode(4))); // add explicit this parameter

        // mutate method name
        n.set(3, "__" + currentClassName + "::" + methodName);

        // identify main method
        if ("main".equals(methodName) &&
                JavaEntities.hasModifier(methodType, "static") &&
                JavaEntities.hasModifier(methodType, "public"))
            mainMethodClassName = currentClassName;

        prevHierarchy.add(n);

        visit(n);
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

    private GNode makeThisExpression() {
        GNode _this = GNode.create("ThisExpression", null);
        return _this;
    }

    private GNode addExplicitThisParameter(Node n) {
        GNode formalParameters = GNode.create("FormalParameters");

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

        formalParameters.add(explicitThisParam);

        for (Object o : n)
            formalParameters.add(o);

        return formalParameters;
    }

    private GNode addExplicitThisArgument(Node n) {
        GNode arguments = GNode.create("Arguments");

        GNode explicitThisArg = GNode.create("Argument");
        explicitThisArg.add(GNode.create("PrimaryIdentifier", "__this"));
        arguments.add(explicitThisArg);

        for (Object o : n)
            arguments.add(o);

        return arguments;
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

}
