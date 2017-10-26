package edu.nyu.oop;

import xtc.tree.GNode;

import java.util.Map;

public class HeaderAstBuilder {
    private Map<String, ClassSignature> classTreeMap;

    public HeaderAstBuilder(Map<String, ClassSignature> map) {
        classTreeMap = map;
    }

    public GNode buildHeaderAst() {
        for (String s : classTreeMap.keySet()) {
            ClassSignature c = classTreeMap.get(s);

            DataLayout dl = new DataLayout(c, classTreeMap);


        }
    }

    private createHead() {
        GNode head = GNode.create("Head");
        GNode namespace1 = GNode.create("NamespaceDeclaration");
        GNode namespace2 = GNode.create("NamespaceDeclaration");
    }
}
