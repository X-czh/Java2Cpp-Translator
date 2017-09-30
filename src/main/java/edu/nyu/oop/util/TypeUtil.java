package edu.nyu.oop.util;


import xtc.Constants;
import xtc.tree.Attribute;
import xtc.tree.Node;
import xtc.type.Type;

public class TypeUtil {
    public static boolean isStaticType(Type type) {
        if (type == null) return false;
        Attribute attr  = type.getAttribute(Constants.NAME_STORAGE);
        return "static".equals(attr == null ? null : attr.getValue());
    }

    public static Type getType(Node n) {
        return (Type) n.getProperty(Constants.TYPE);
    }

    public static void setType(Node n, Type type) {
        n.setProperty(Constants.TYPE, type);
    }
}
