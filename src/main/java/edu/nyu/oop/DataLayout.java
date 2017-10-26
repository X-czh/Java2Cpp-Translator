package edu.nyu.oop;

import java.util.*;

import xtc.tree.GNode;

public class DataLayout {
    private ClassSignature thisClass;
    private Map<String, ClassSignature> classTreeMap;
    private List<FieldSignature> fieldMap;

    public DataLayout(ClassSignature c, Map<String, ClassSignature> map) {
        thisClass = c;
        classTreeMap = map;
        fieldMap = new ArrayList<>();
        fillFieldMap(thisClass);
    }

    public GNode makeDataLayout() {
        GNode classDec = GNode.create("ClassDeclaration");
        classDec.add(null);
        classDec.add("__" + thisClass.getClassName());
        classDec.add(null);
        classDec.add(null);
        classDec.add(null);

        // class body
        GNode classBody = GNode.create("ClassBody");
        for (ConstructorSignature c : thisClass.getConstructorList())
            classBody.add(makeConstructorDeclarationNode(c));
        for (FieldSignature f : fieldMap)
            classBody.add(makeFieldDeclarationNode(f));
        for (MethodSignature m : thisClass.getMethodList())
            classBody.add(makeMethodDeclarationNode(m));
        classDec.add(classBody);

        return classDec;
    }

    private void fillFieldMap(ClassSignature c) {
        if (c.getParentClassName() != null)
            fillFieldMap(classTreeMap.get(c.getParentClassName()));
        fieldMap.addAll(c.getFieldList());
    }

    private GNode makeConstructorDeclarationNode(ConstructorSignature c) {
        GNode constrDec = GNode.create("ConstructorDeclaration");

        constrDec.add(null);
        constrDec.add(null);

        // name
        constrDec.add(c.getName());

        // parameters
        GNode params = GNode.create("FormalParameters");
        for (int i = 0; i < c.getParameters().size(); i++) {
            GNode param = GNode.create("FormalParameter");
            param.add(null);
            param.add(c.getParameterTypes().get(i));
            param.add(null);
            param.add(c.getParameters().get(i));
            param.add(null);
            params.add(param);
        }
        constrDec.add(params);

        // initializations
        constrDec.add(null);

        // block
        constrDec.add(null);

        return constrDec;
    }

    private GNode makeFieldDeclarationNode(FieldSignature f) {
        GNode fieldDec = GNode.create("FieldDeclaration");

        // modifiers
        GNode modifiers = GNode.create("Modifiers");
        for (String s : f.getModifier()) {
            if (s.equals("static")) {
                GNode modifier = GNode.create("Modifier");
                modifier.add(s);
                modifiers.add(modifier);
                break;
            }
        }
        fieldDec.add(modifiers);

        // type
        fieldDec.add(f.getType());

        // declarators
        GNode declarators = GNode.create("Declarators");
        for (String s : f.getDeclarators()) {
            GNode declarator = GNode.create("Declarator");
            declarator.add(s);
            declarators.add(declarator);
        }
        fieldDec.add(declarators);

        return fieldDec;
    }

    private GNode makeMethodDeclarationNode(MethodSignature m) {
        GNode methodDec = GNode.create("MethodDeclaration");

        // modifiers
        GNode modifiers = GNode.create("Modifiers");
        for (String s : m.getModifier()) {
            if (s.equals("static")) {
                GNode modifier = GNode.create("Modifier");
                modifier.add(s);
                modifiers.add(modifier);
                break;
            }
        }
        methodDec.add(modifiers);

        methodDec.add(null);

        // return type
        methodDec.add(m.getReturnType());

        // method name
        methodDec.add(m.getMethodName());

        // parameters
        GNode params = GNode.create("FormalParameters");
        for (int i = 0; i < m.getParameters().size(); i++) {
            GNode param = GNode.create("FormalParameter");
            param.add(null);
            param.add(m.getParameterTypes().get(i));
            param.add(null);
            param.add(m.getParameters().get(i));
            param.add(null);
            params.add(param);
        }
        methodDec.add(params);

        methodDec.add(null);
        methodDec.add(null);

        // block
        methodDec.add(null);

        return methodDec;
    }
}
