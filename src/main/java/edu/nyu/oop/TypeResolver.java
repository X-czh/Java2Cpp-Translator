package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;

import java.util.*;

/**
 * This class translates Java primitive types to their C++ equivalences.
 * It also provides util methods for type handling when generating the header AST.
 */
public class TypeResolver {
    private static final Map<String, String> primitiveTypeMap;

    static {
        primitiveTypeMap = new HashMap<>();
        primitiveTypeMap.put("boolean", "bool");
        primitiveTypeMap.put("byte", "int8_t");
        primitiveTypeMap.put("char", "char"); // as handling UTF-16 characters in C++ is a bit of a mess, we choose not to translate it
        primitiveTypeMap.put("short", "int16_t");
        primitiveTypeMap.put("int", "int32_t");
        primitiveTypeMap.put("long", "int64_t");
        primitiveTypeMap.put("float", "float");
        primitiveTypeMap.put("double", "double");
    }

    public static Node createType(String name, Node dimension) {
        if (name.equals("void"))
            return GNode.create("VoidType");

        GNode type = GNode.create("Type");
        GNode temp1;
        if (primitiveTypeMap.containsKey(name)) {
            temp1 = GNode.create("PrimitiveType");
            temp1.add(primitiveTypeMap.get(name));
        } else {
            temp1 = GNode.create("QualifiedIdentifier");
            temp1.add(name);
        }
        type.add(temp1);
        type.add(dimension);
        return type;
    }

    public static String typeToString(Node type) {
        // check whether it is void type
        if ("VoidType".equals(type.getName()))
            return "void";

        String typeStr;
        Node dimension = type.getNode(1);

        if (dimension == null) {
            typeStr = type.getNode(0).getString(0);
            if (primitiveTypeMap.containsKey(typeStr))
                typeStr = primitiveTypeMap.get(typeStr);
        }
        else {
            String componentType = type.getNode(0).getString(0);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < type.getNode(1).size(); i++)
                sb.append("__rt::Array<");
            sb.append(componentType);
            for (int i = 0; i < type.getNode(1).size(); i++)
                sb.append(">");
            typeStr = sb.toString();
        }

        return typeStr;
    }

    public static String javaTypeToString(Node type) {
        // check whether it is void type
        if ("VoidType".equals(type.getName()))
            return "void";

        String typeStr;
        Node dimension = type.getNode(1);

        if (dimension == null) {
            typeStr = type.getNode(0).getString(0);
        } else {
            typeStr = type.getNode(0).getString(0);
        }

        return typeStr;
    }
}
