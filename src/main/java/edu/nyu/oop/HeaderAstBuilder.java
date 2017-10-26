package edu.nyu.oop;

import xtc.tree.GNode;

import java.util.List;
import java.util.Map;

public class HeaderAstBuilder {
    private Map<String, ClassSignature> classTreeMap;
    private List<String> packageInfo;

    public HeaderAstBuilder(Map<String, ClassSignature> map, List<String> lst) {
        classTreeMap = map;
        packageInfo = lst;
    }

    public GNode buildHeaderAst() {
        for (String s : classTreeMap.keySet()) {
            ClassSignature c = classTreeMap.get(s);
        }
        return null;
    }

    private void createHead() {
        GNode compilationUnit = GNode.create("CompilationUnit");
        GNode namespace1 = GNode.create("NamespaceDeclaration");
        GNode namespace2 = GNode.create("NamespaceDeclaration");

        GNode forwardDecs = GNode.create("ForwardDeclarations");
        GNode typespecs = GNode.create("TypeSpecifiers");
        GNode forwardDec, typespec;

        for (String s : classTreeMap.keySet()) {
            String className = classTreeMap.get(s).getClassName();
            if (className != "Object" && className != "String" && className != "Class") {
                // forward declaration
                forwardDec = GNode.create("ForwardDeclaration");
                forwardDec.add("struct");
                forwardDec.add("__" + className);
                forwardDecs.add(forwardDec);
                forwardDec = GNode.create("ForwardDeclaration");
                forwardDec.add("struct");
                forwardDec.add("__" + className + "_VT");
                forwardDecs.add(forwardDec);

                // type specifier
                typespec = GNode.create("TypeSpecifier");
                typespec.add("typedef");
                typespec.add("__" + className + "*");
                typespec.add(className);
                typespecs.add(typespec);
            }
        }
    }
}
