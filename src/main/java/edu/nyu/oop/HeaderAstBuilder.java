package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;

import java.util.List;
import java.util.Map;

/**
 * This class is for building up a header AST.
 */
public class HeaderAstBuilder {
    private Map<String, ClassSignature> classTreeMap;
    private List<String> packageInfo;

    public HeaderAstBuilder(Map<String, ClassSignature> map, List<String> lst) {
        classTreeMap = map;
        packageInfo = lst;
    }

    public GNode buildHeaderAst() {
        // compilation unit
        GNode compilationUnit = GNode.create("CompilationUnit");
        GNode prevHierarchy = compilationUnit;

        // namespaces
        for (String s : packageInfo) {
            GNode namespace = GNode.create("NamespaceDeclaration");
            namespace.add(s);
            prevHierarchy.add(namespace);
            prevHierarchy = namespace;
        }

        // forward declarations and type specifiers
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
                typespec.add("__rt::Ptr<" + "__" + className + ">");
                typespec.add(className);
                typespecs.add(typespec);
            }
        }

        prevHierarchy.add(forwardDecs);
        prevHierarchy.add(typespecs);

        // DataLayouts
        for (String s : classTreeMap.keySet()) {
            ClassSignature c = classTreeMap.get(s);
            if (s.compareTo("Object") != 0 && s.compareTo("String") != 0 && s.compareTo("Class") != 0) {
                DataLayout dl = new DataLayout(c, classTreeMap);
                prevHierarchy.add(dl.makeDataLayout());
            }
        }

        // VTables
        VTable vt = new VTable();
        for (Node n: vt.getVTable(classTreeMap))
            prevHierarchy.add(n);

        // return full AST
        return compilationUnit;
    }
}
