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
        classBody.add(makePtrToVtableField());
        for (FieldSignature f : fieldMap)
            classBody.add(makeFieldDeclaration(f));
        classBody.add(makeConstructorDeclaration());
        for (ConstructorSignature c : thisClass.getConstructorList())
            classBody.add(makeInitMethod(c));
        for (MethodSignature m : thisClass.getMethodList())
            classBody.add(makeMethodDeclaration(m));
        classBody.add(makeReturnClassMethod());
        classBody.add(makeVtableField());
        classDec.add(classBody);

        return classDec;
    }

    private void fillFieldMap(ClassSignature c) {
        if (c.getParentClassName() != null)
            fillFieldMap(classTreeMap.get(c.getParentClassName()));

        if (c.equals(thisClass))
            fieldMap.addAll(c.getFieldList());
        else {
            for (FieldSignature f : c.getFieldList()) {
                if (!f.getModifier().contains("static"))
                    fieldMap.add(f);
            }
        }
    }

    private GNode makePtrToVtableField() {
        FieldSignature f = new FieldSignature(
                null,
                "__" + thisClass.getClassName() + "_VT*",
                Arrays.asList("__vptr")
        );
        return makeFieldDeclaration(f);
    }

    private GNode makeInitMethod(ConstructorSignature c) {
        List<String> params = new ArrayList<>();
        List<String> paramTypes = new ArrayList<>();
        params.add("__this");
        params.addAll(c.getParameters());
        paramTypes.add(thisClass.getClassName());
        paramTypes.addAll(c.getParameterTypes());

        MethodSignature m = new MethodSignature(
                Arrays.asList("static"),
                thisClass.getClassName(),
                "__init",
                params,
                paramTypes
        );

        return makeMethodDeclaration(m);
    }

    private GNode makeReturnClassMethod() {
        MethodSignature m = new MethodSignature(
                Arrays.asList("static"),
                "Class",
                "__class",
                null,
                null
        );
        return makeMethodDeclaration(m);
    }

    private GNode makeVtableField() {
        FieldSignature f = new FieldSignature(
                Arrays.asList("static"),
                "__" + thisClass.getClassName() + "_VT",
                Arrays.asList("__vtable")
        );
        return makeFieldDeclaration(f);
    }

    private GNode makeFieldDeclaration(FieldSignature f) {
        GNode fieldDec = GNode.create("FieldDeclaration");

        // modifiers
        GNode modifiers = GNode.create("Modifiers");
        if (f.getModifier().contains("static")) {
            GNode modifier = GNode.create("Modifier");
            modifier.add("static");
            modifiers.add(modifier);
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

    private GNode makeConstructorDeclaration() {
        GNode constrDec = GNode.create("ConstructorDeclaration");

        constrDec.add(null);
        constrDec.add(null);

        // name
        constrDec.add(thisClass.getClassName());

        // parameters
        constrDec.add(null);

        // initializations
        constrDec.add(null);

        // block
        constrDec.add(null);

        return constrDec;
    }

    private GNode makeMethodDeclaration(MethodSignature m) {
        GNode methodDec = GNode.create("MethodDeclaration");

        // modifiers
        GNode modifiers = GNode.create("Modifiers");
        if (m.getModifier().contains("static")) {
            GNode modifier = GNode.create("Modifier");
            modifier.add("static");
            modifiers.add(modifier);
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
