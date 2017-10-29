package edu.nyu.oop;

import java.util.Arrays;
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
        MethodSignature equals = new MethodSignature(modifiers, "boolean", "equals", params, param_types);

        object_class.addMethod(toString);
        object_class.addMethod(hashCode);
        object_class.addMethod(getClass);
        object_class.addMethod(equals);
        return object_class;
    }

    public static ClassSignature buildString() {
        ClassSignature string_class = new ClassSignature("String","Object");

        FieldSignature data = new FieldSignature(new ArrayList<>(), "String", Arrays.asList("data"));

        ConstructorSignature constr = new ConstructorSignature("String", Arrays.asList("data"), Arrays.asList("String"));

        MethodSignature hashCode = new MethodSignature(new ArrayList<>(), "int", "hashCode", null, null);
        MethodSignature toString = new MethodSignature(new ArrayList<>(), "String", "toString", null, null);
        MethodSignature length = new MethodSignature(new ArrayList<>(), "int", "length", null, null);
        MethodSignature equals = new MethodSignature(new ArrayList<>(), "int", "equals", null, Arrays.asList("Object"));
        MethodSignature charAt = new MethodSignature(new ArrayList<>(), "char", "charAt", null, Arrays.asList("int"));

        string_class.addField(data);
        string_class.addConstructor(constr);
        string_class.addMethod(hashCode);
        string_class.addMethod(equals);
        string_class.addMethod(toString);
        string_class.addMethod(length);
        string_class.addMethod(charAt);

        return string_class;
    }

    public static ClassSignature buildClass() {
        ClassSignature class_class = new ClassSignature("Class", "Object");

        FieldSignature name = new FieldSignature(new ArrayList<>(), "String", Arrays.asList("name"));
        FieldSignature parent = new FieldSignature(new ArrayList<>(), "Class", Arrays.asList("parent"));

        ArrayList<String> params = new ArrayList<>();
        ArrayList<String> param_types = new ArrayList<>();
        params.add("name");
        param_types.add("String");
        params.add("parent");
        param_types.add("Class");
        ConstructorSignature constr = new ConstructorSignature("Class", params, param_types);

        MethodSignature toString = new MethodSignature(new ArrayList<>(), "String", "toString", null, null);
        MethodSignature getName = new MethodSignature(new ArrayList<>(), "Class", "getName", null, null);
        MethodSignature getSuperclass = new MethodSignature(new ArrayList<>(), "Class", "getSuperclass", null, null);
        MethodSignature isInstance = new MethodSignature(new ArrayList<>(), "boolean", "isInstance", null, Arrays.asList("Object"));

        class_class.addField(name);
        class_class.addField(parent);
        class_class.addConstructor(constr);
        class_class.addMethod(toString);
        class_class.addMethod(getName);
        class_class.addMethod(getSuperclass);
        class_class.addMethod(isInstance);

        return class_class;
    }
}