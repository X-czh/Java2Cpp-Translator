package edu.nyu.oop;

import org.slf4j.Logger;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class generates the virtual class tables for the classes.
 */
public class VTable {

    public Node create_field_dec(ArrayList<String> modifiers, Node type, String name){
        GNode field = GNode.create("FieldDeclaration");
        GNode modifi_node = GNode.create("Modifiers");
        for (String modify_name:modifiers){
            GNode temp = GNode.create("Modifier");
            temp.add(modify_name);
            modifi_node.add(temp);
        }
        field.add(modifi_node);
        field.add(type);
        GNode dec = GNode.create("Declarators");
        GNode sub_dec = GNode.create("Declarator");
        sub_dec.add(name);
        sub_dec.add(null);
        sub_dec.add(null);
        dec.add(sub_dec);
        field.add(dec);
        return field;
    }

    public Node createInit(String first, String second){
        GNode init = GNode.create("Initialize");
        init.add(first);
        init.add(second);
        return init;
    }

    public Node create_vt_constructor(ArrayList<MethodSignature> methods, String name){
        GNode constructor = GNode.create("ConstructorDeclaration");
        constructor.add(null);
        constructor.add(null);
        constructor.add("__"+name+"_VT");
        constructor.add(GNode.create("FormalParameters"));
        GNode init_list = GNode.create("Initializations");

        init_list.add(createInit("__is_a", "__"+name+"::__class()"));
        for (MethodSignature m : methods){
            if (m.getOwner().compareTo(name)==0) {
                init_list.add(createInit(m.getMethodName(), "&__"+name+"::"+m.getMethodName()));
            }
            else {
                String first = m.getMethodName();
                String second = "";
                second += "(" + TypeResolver.typeToString(m.getReturnType()) + " (*)("+name;
                for (Node t: m.getParameterTypes()){
                    second += ", " + TypeResolver.typeToString(t);
                }
                second += ")) &__" + m.getOwner() + "::" + m.getMethodName();
                init_list.add(createInit(first, second));
            }
        }

        constructor.add(init_list);
        constructor.add(GNode.create("Block"));
        return  constructor;
    }

    public Node createVTable(ArrayList<MethodSignature> methods, String class_name){
        GNode Vtable = GNode.create("ClassDeclaration");
        Vtable.add(null);
        Vtable.add("__"+class_name+"_VT");
        Vtable.add(null);
        Vtable.add(null);
        Vtable.add(null);
        GNode VtableClassBody = GNode.create("ClassBody");
        VtableClassBody.add(create_field_dec(new ArrayList<String>(), TypeResolver.createType("Class", null), "__is_a"));
        for (MethodSignature m: methods){
            String extended_name;
            extended_name = "(*" + m.getMethodName() + ")(" + class_name + ", ";
            for (Node param_t : m.getParameterTypes()) {
                extended_name += TypeResolver.typeToString(param_t) + ", ";
            }
            extended_name = extended_name.substring(0, extended_name.length()-2);
            extended_name += ")";
            VtableClassBody.add(create_field_dec(new ArrayList<String>(), m.getReturnType(), extended_name));
        }

        VtableClassBody.add(create_vt_constructor(methods, class_name));
        Vtable.add(VtableClassBody);

        return  Vtable;
    }

    public boolean checkMethod(MethodSignature m) {
        for (String modifi : m.getModifier()){
            if (modifi.compareTo("static") == 0 || modifi.compareTo("private") == 0) return false;
        }
        return true;
    }

    public ArrayList<Node> getVTable(Map<String, ClassSignature> map) {

        ArrayList<Node> VTs= new ArrayList<Node>();
        for (String k: map.keySet()) {
            if (k.compareTo("Object") != 0 && k.compareTo("String") != 0 && k.compareTo("Class") != 0) {
                ArrayList<MethodSignature> methods = new ArrayList<MethodSignature>();

                String current_class_name = k;
                while (current_class_name.compareTo("null") != 0) {
                    ClassSignature current_class = map.get(current_class_name);
                    for (MethodSignature m : current_class.getMethodList()) {
                        if (checkMethod(m)) {
                            boolean find = false;
                            for (MethodSignature existed : methods) {
                                if (existed.getMethodName().compareTo(m.getMethodName()) == 0) find = true;
                            }
                            if (!find) {
                                m.setOwner(current_class_name);
                                methods.add(m);
                            }
                        }
                    }
                    current_class_name = current_class.getParentClassName();
                }

                VTs.add(createVTable(methods, k));
            }
        }
        return VTs;
    }
}
