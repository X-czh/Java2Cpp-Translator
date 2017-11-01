package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;

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

    public static Node createType(String name, Node dimension){
        GNode type = GNode.create("Type");
        GNode temp1 = GNode.create("QualifiedIdentifier");
        temp1.add(name);
        type.add(temp1);
        type.add(null);
        return type;
    }

    public static String typeToString(Node type){
        String typeString = type.getNode(0).getString(0);

        if (typeString.compareTo("boolean") == 0){
            return "bool";
        } else if (typeString.compareTo("int") == 0) {
            return "int32_t";
        } else if (typeString.compareTo("long") == 0) {
            return "int64_t";
        }

        return typeString;
    }

    public static ClassSignature buildObject() {
        ClassSignature object_class = new ClassSignature("Object", "null");

        MethodSignature toString = new MethodSignature(Arrays.asList("public"), createType("String", null), "toString", new ArrayList<String>(), new ArrayList<Node>());
        MethodSignature hashCode = new MethodSignature(Arrays.asList("public"), createType("int", null), "hashCode", new ArrayList<String>(), new ArrayList<Node>());
        MethodSignature getClass = new MethodSignature(Arrays.asList("public"), createType("Class", null), "getClass", new ArrayList<String>(), new ArrayList<Node>());
        MethodSignature equals = new MethodSignature(Arrays.asList("public"), createType("boolean", null), "equals", Arrays.asList("other"), Arrays.asList(ClassSignature.createType("Object", null)));

        object_class.addMethod(toString);
        object_class.addMethod(hashCode);
        object_class.addMethod(getClass);
        object_class.addMethod(equals);
        return object_class;
    }

    public static ClassSignature buildString() {
        ClassSignature string_class = new ClassSignature("String","Object");


        FieldSignature data = new FieldSignature(new ArrayList<>(), createType("String", null), Arrays.asList("data"));

        ConstructorSignature constr = new ConstructorSignature("String", Arrays.asList("data"), Arrays.asList(ClassSignature.createType("String", null)));

        MethodSignature hashCode = new MethodSignature(new ArrayList<>(), createType("int", null), "hashCode", null, null);
        MethodSignature toString = new MethodSignature(new ArrayList<>(), createType("String", null), "toString", null, null);
        MethodSignature length = new MethodSignature(new ArrayList<>(), createType("int", null), "length", null, null);
        MethodSignature equals = new MethodSignature(new ArrayList<>(), createType("int", null), "equals", null, Arrays.asList(ClassSignature.createType("Object", null)));
        MethodSignature charAt = new MethodSignature(new ArrayList<>(), createType("char", null), "charAt", null, Arrays.asList(ClassSignature.createType("int", null)));

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

        GNode String_type = GNode.create("Type");
        GNode temp1 = GNode.create("QualifiedIdentifier");
        temp1.add("String");
        String_type.add(temp1);
        String_type.add(null);

        FieldSignature name = new FieldSignature(new ArrayList<>(), createType("String", null), Arrays.asList("name"));
        FieldSignature parent = new FieldSignature(new ArrayList<>(), createType("Class", null), Arrays.asList("parent"));

        ArrayList<String> params = new ArrayList<>();
        ArrayList<Node> param_types = new ArrayList<>();
        params.add("name");
        param_types.add(ClassSignature.createType("String", null));
        params.add("parent");
        param_types.add(ClassSignature.createType("Class", null));
        ConstructorSignature constr = new ConstructorSignature("Class", params, param_types);

        MethodSignature toString = new MethodSignature(new ArrayList<>(), createType("String", null), "toString", null, null);
        MethodSignature getName = new MethodSignature(new ArrayList<>(), createType("Class", null), "getName", null, null);
        MethodSignature getSuperclass = new MethodSignature(new ArrayList<>(), createType("Class", null), "getSuperclass", null, null);
        MethodSignature isInstance = new MethodSignature(new ArrayList<>(), createType("boolean", null), "isInstance", null, Arrays.asList(ClassSignature.createType("Object", null)));

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