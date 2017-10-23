package edu.nyu.oop;

import xtc.tree.GNode;

import java.util.ArrayList;

public class ClassSignature {
    private String class_name;
    private String parent_class_name;
    private ArrayList<GNode> field_list;
    private ArrayList<GNode> method_list;
    private ArrayList<GNode> constructor_list;

    public ClassSignature(String class_name, String parent_class_name) {
        this.parent_class_name = parent_class_name;
        this.class_name = class_name;
        this.field_list = new ArrayList<>();
        this.method_list = new ArrayList<>();
        this.constructor_list = new ArrayList<>();
    }

    public String getClassName() {
        return class_name;
    }

    public String getParentClassName() {
        return parent_class_name;
    }

    public ArrayList<GNode> getFieldList() {
        return field_list;
    }

    public ArrayList<GNode> getMethodList() {
        return method_list;
    }

    public ArrayList<GNode> getConstructorList() {
        return constructor_list;
    }

    public void addField(GNode n) {
        this.field_list.add(n);
    }

    public void addMethod(GNode n) {
        this.method_list.add(n);
    }

    public void addConstructor(GNode n) {
        this.constructor_list.add(n);
    }

    /**
     * The following are static fields and methods used for prepopulating
     * class signatures of Object, String and Class
     */

    private static final GNode type_int =
            GNode.create("Type", GNode.create("PrimitiveType", "int"), null);
    private static final GNode type_bool =
            GNode.create("Type", GNode.create("PrimitiveType", "bool"), null);
    private static final GNode type_char =
            GNode.create("Type", GNode.create("PrimitiveType", "char"), null);
    private static final GNode type_object =
            GNode.create("Type", GNode.create("QualifiedIdentifier", "Object"), null);
    private static final GNode type_string =
            GNode.create("Type", GNode.create("QualifiedIdentifier", "String"), null);
    private static final GNode type_class =
            GNode.create("Type", GNode.create("QualifiedIdentifier", "Class"), null);

    public static ClassSignature buildObject() {
        ClassSignature cs = new ClassSignature("Object", null);

        GNode param = GNode.create(
                "FormalParameters",
                GNode.create("FormalParameter", null, type_object, null, null, null)
        );
        cs.addConstructor(buildConstructorDeclaration("Object", null));
        cs.addMethod(buildMethodDeclaration(type_int, "hashCode", null));
        cs.addMethod(buildMethodDeclaration(type_bool, "equals", param));
        cs.addMethod(buildMethodDeclaration(type_class, "getClass", null));
        cs.addMethod(buildMethodDeclaration(type_string, "toString", null));

        return cs;
    }

    public static ClassSignature buildString() {
        ClassSignature cs = new ClassSignature("String", "Object");

        GNode param1 = GNode.create(
                "FormalParameters",
                GNode.create("FormalParameter", null, type_string, null, "data", null)
        );
        GNode param2 = GNode.create(
                "FormalParameters",
                GNode.create("FormalParameter", null, type_object, null, null, null)
        );
        GNode param3 = GNode.create(
                "FormalParameters",
                GNode.create("FormalParameter", null, type_int, null, null, null)
        );
        cs.addField(buildFieldDeclaration(type_string, "data"));
        cs.addConstructor(buildConstructorDeclaration("String", param1));
        cs.addMethod(buildMethodDeclaration(type_int, "hashCode", null));
        cs.addMethod(buildMethodDeclaration(type_bool, "equals", param2));
        cs.addMethod(buildMethodDeclaration(type_string, "toString", null));
        cs.addMethod(buildMethodDeclaration(type_int, "length", null));
        cs.addMethod(buildMethodDeclaration(type_char, "charAt", param3));

        return cs;
    }

    public static ClassSignature buildClass() {
        ClassSignature cs = new ClassSignature("Class", "Object");

        GNode param1 = GNode.create(
                "FormalParameters",
                GNode.create("FormalParameter", null, type_string, null, "name", null),
                GNode.create("FormalParameter", null, type_class, null, "parent", null)
        );
        GNode param2 = GNode.create(
                "FormalParameters",
                GNode.create("FormalParameter", null, type_object, null, null, null)
        );
        cs.addField(buildFieldDeclaration(type_string, "name"));
        cs.addField(buildFieldDeclaration(type_class, "parent"));
        cs.addConstructor(buildConstructorDeclaration("Class", param1));
        cs.addMethod(buildMethodDeclaration(type_string, "toString", null));
        cs.addMethod(buildMethodDeclaration(type_string, "getName", null));
        cs.addMethod(buildMethodDeclaration(type_class, "getSuperClass", null));
        cs.addMethod(buildMethodDeclaration(type_bool, "isInstance", param2));

        return cs;
    }

    private static GNode buildFieldDeclaration(GNode type, String name) {
        return GNode.create(
                "FieldDeclaration",
                null,
                type,
                GNode.create("Declarators", GNode.create("Declarator", name, null, null))
        );
    }

    private static GNode buildMethodDeclaration(GNode type, String name, GNode param) {
        return GNode.create(
                "MethodDeclaration",
                null,
                null,
                type,
                name,
                param,
                null,
                null,
                null
        );
    }

    private static GNode buildConstructorDeclaration(String name, GNode param) {
        return GNode.create(
                "ConstructorDeclaration",
                null,
                null,
                name,
                param,
                null,
                null
        );
    }
}