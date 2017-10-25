package edu.nyu.oop;

import com.sun.deploy.association.utility.GnomeAssociationUtil;
import org.slf4j.Logger;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class VTable {

    public Node create_field_dec(ArrayList<String> modifiers, String type, String name){
        GNode field = GNode.create("FieldDeclaration");
        GNode modifi_node = GNode.create("Modifiers");
        for (String modify_name:modifiers){
            GNode temp = GNode.create("Modifier");
            temp.add(modify_name);
            modifi_node.add(temp);
        }
        field.add(modifi_node);
        GNode t = GNode.create("Type");
        GNode identifier = GNode.create("QualifiedIdentifier");
        identifier.add(type);
        t.add(identifier);
        field.add(t);
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
                init_list.add(createInit(m.getMethodName(), "__"+name+"::"+m.getMethodName()));
            }
            else {
                String first = m.getMethodName();
                String second = "";
                second += "(" + m.getReturnType() + " (*)("+name;
                for (String t:m.getParameterTypes()){
                    second += ", " + t;
                }
                second += ") &__" + m.getOwner() + "::" + m.getMethodName();
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
        VtableClassBody.add(create_field_dec(null, "Class", "__is_a"));
        for (MethodSignature m: methods){
            String extended_name;
            extended_name = "(*" + m.getMethodName() + ")(";
            for (String param_t : m.getParameterTypes()) {
                extended_name += param_t + ", ";
            }
            extended_name = extended_name.substring(0, extended_name.length()-2);
            extended_name += ")";
            VtableClassBody.add(create_field_dec(null, m.getReturnType(), extended_name));
        }

        VtableClassBody.add(create_vt_constructor(methods, class_name));
        Vtable.add(VtableClassBody);

        return  Vtable;
    }

    public ArrayList<Node> getVTable(HashMap<String, ClassSignature> map) {

        ArrayList<Node> VTs= new ArrayList<Node>();
        for (String k: map.keySet()) {

            ArrayList<MethodSignature> methods = new ArrayList<MethodSignature>();

            String current_class_name = k;
            while (current_class_name.compareTo("null") != 0) {
                ClassSignature current_class = map.get(k);
                for (MethodSignature m : current_class.getMethodList()) {
                    boolean find = false;
                    for (MethodSignature existed : methods) {
                        if (existed.getMethodName().compareTo(m.getMethodName()) == 0) find = true;
                    }
                    if (!find) {
                        m.setOwner(current_class_name);
                        methods.add(m);
                    }
                }
                current_class_name = current_class.getParentClassName();
            }

            VTs.add(createVTable(methods, k));

        }
        return VTs;
    }
}
