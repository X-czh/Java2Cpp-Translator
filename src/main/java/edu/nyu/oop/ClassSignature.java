package edu.nyu.oop;


import xtc.tree.GNode;
import xtc.tree.Node;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * A ClassSignature encapsulates the sufficient info to characterise a class.
 * It also provides util methods for pre-populating Object, String, Class.
 */
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

        MethodSignature toString = new MethodSignature(Arrays.asList("public"), TypeResolver.createType("String", null), "toString", new ArrayList<String>(), new ArrayList<Node>());
        MethodSignature hashCode = new MethodSignature(Arrays.asList("public"), TypeResolver.createType("int", null), "hashCode", new ArrayList<String>(), new ArrayList<Node>());
        MethodSignature getClass = new MethodSignature(Arrays.asList("public"), TypeResolver.createType("Class", null), "getClass", new ArrayList<String>(), new ArrayList<Node>());
        MethodSignature equals = new MethodSignature(Arrays.asList("public"), TypeResolver.createType("boolean", null), "equals", Arrays.asList("other"), Arrays.asList(TypeResolver.createType("Object", null)));

        object_class.addMethod(toString);
        object_class.addMethod(hashCode);
        object_class.addMethod(getClass);
        object_class.addMethod(equals);
        return object_class;
    }

    public static ClassSignature buildString() {
        ClassSignature string_class = new ClassSignature("String","Object");


        FieldSignature data = new FieldSignature(new ArrayList<>(), TypeResolver.createType("String", null), Arrays.asList("data"));

        ConstructorSignature constr = new ConstructorSignature("String", Arrays.asList("data"), Arrays.asList(TypeResolver.createType("String", null)));

        MethodSignature hashCode = new MethodSignature(new ArrayList<>(), TypeResolver.createType("int", null), "hashCode", null, null);
        MethodSignature toString = new MethodSignature(new ArrayList<>(), TypeResolver.createType("String", null), "toString", null, null);
        MethodSignature length = new MethodSignature(new ArrayList<>(), TypeResolver.createType("int", null), "length", null, null);
        MethodSignature equals = new MethodSignature(new ArrayList<>(), TypeResolver.createType("int", null), "equals", null, Arrays.asList(TypeResolver.createType("Object", null)));
        MethodSignature charAt = new MethodSignature(new ArrayList<>(), TypeResolver.createType("char", null), "charAt", null, Arrays.asList(TypeResolver.createType("int", null)));

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

        FieldSignature name = new FieldSignature(new ArrayList<>(), TypeResolver.createType("String", null), Arrays.asList("name"));
        FieldSignature parent = new FieldSignature(new ArrayList<>(), TypeResolver.createType("Class", null), Arrays.asList("parent"));

        ArrayList<String> params = new ArrayList<>();
        ArrayList<Node> param_types = new ArrayList<>();
        params.add("name");
        param_types.add(TypeResolver.createType("String", null));
        params.add("parent");
        param_types.add(TypeResolver.createType("Class", null));
        ConstructorSignature constr = new ConstructorSignature("Class", params, param_types);

        MethodSignature toString = new MethodSignature(new ArrayList<>(), TypeResolver.createType("String", null), "toString", null, null);
        MethodSignature getName = new MethodSignature(new ArrayList<>(), TypeResolver.createType("Class", null), "getName", null, null);
        MethodSignature getSuperclass = new MethodSignature(new ArrayList<>(), TypeResolver.createType("Class", null), "getSuperclass", null, null);
        MethodSignature isInstance = new MethodSignature(new ArrayList<>(), TypeResolver.createType("boolean", null), "isInstance", null, Arrays.asList(TypeResolver.createType("Object", null)));

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