package edu.nyu.oop;

import java.util.List;
import java.util.ArrayList;

public class ClassSignature {
    private String class_name;
    private String parent_class_name;
    private ArrayList<FieldSignature> field_list;
    private ArrayList<MethodSignature> method_list;
    private ArrayList<ConstructorSignature> constructor_list;

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

    public ArrayList<FieldSignature> getFieldList() {
        return field_list;
    }

    public ArrayList<MethodSignature> getMethodList() {
        return method_list;
    }

    public ArrayList<ConstructorSignature> getConstructorList() {
        return constructor_list;
    }

    public void addField(FieldSignature n) {
        this.field_list.add(n);
    }

    public void addMethod(MethodSignature n) {
        this.method_list.add(n);
    }

    public void addConstructor(ConstructorSignature n) {
        this.constructor_list.add(n);
    }

    public static ClassSignature buildObject() {
        ClassSignature object = new ClassSignature("Object",null);
        MethodSignature toString = new MethodSignature("public", "String", "toString", null,null);
        MethodSignature hashCode = new MethodSignature("public", "int", "hashCode",null,null);
        MethodSignature getClass = new MethodSignature("public","Class", "getClass", null, null);
        MethodSignature isA = new MethodSignature("public", "Class", "isA", null, null);
        MethodSignature equals = new MethodSignature("public", "Boolean", "equals", null, null);

        object.addMethod(toString);
        object.addMethod(hashCode);
        object.addMethod(getClass);
        object.addMethod(isA);
        object.addMethod(equals);
    }

    public static ClassSignature buildString() {
        ClassSignature string = new ClassSignature("String",null);
    }

    public static ClassSignature buildClass() {
        ClassSignature our_class = new ClassSignature("Class",null);
    }

}