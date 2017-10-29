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
        ClassSignature object_class = new ClassSignature("Object", "null");
        ArrayList<String> modifiers = new ArrayList<String>();
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<String> param_types = new ArrayList<String>();

        modifiers.add("public");
        MethodSignature toString = new MethodSignature(modifiers, "String", "toString", params, param_types);
        MethodSignature hashCode = new MethodSignature(modifiers, "int", "hashCode", params, param_types);
        MethodSignature getClass = new MethodSignature(modifiers, "Class", "getClass", params, param_types);
        params.add("other");
        param_types.add("Object");
        MethodSignature equals = new MethodSignature(modifiers, "Boolean", "equals", params, param_types);

        object_class.addMethod(toString);
        object_class.addMethod(hashCode);
        object_class.addMethod(getClass);
        object_class.addMethod(equals);
        return object_class;
    }

    public static ClassSignature buildString() {
        ClassSignature string = new ClassSignature("String",null);
    }

    public static ClassSignature buildClass() {
        ClassSignature our_class = new ClassSignature("Class",null);
    }

}