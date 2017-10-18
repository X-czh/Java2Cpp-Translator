package edu.nyu.oop.util;

import org.slf4j.Logger;
import xtc.Constants;
import xtc.type.Type;
import xtc.type.VariableT;
import xtc.util.SymbolTable;
import xtc.util.SymbolTable.Scope;
import xtc.type.*;
import xtc.type.Type.Tag;
import xtc.tree.Node;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

public class SymbolTableUtil {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(SymbolTableUtil.class);

    // Takes a Scope object representing a method and extract a list of parameters
    public static List<VariableT> extractFormalParams(Scope method) {
        List<VariableT> parameters = new LinkedList<>();

        Iterator<String> i = method.symbols();
        while (i.hasNext()) {
            Object o = method.lookup(i.next());
            if (o instanceof VariableT) {
                VariableT v = (VariableT) o;
                if (v.getKind() == VariableT.Kind.PARAMETER) {
                    parameters.add(v);
                }
            }
        }

        return parameters;
    }

    public static void enterScope(SymbolTable table, Node n) {
        String name = n.getStringProperty(Constants.SCOPE);
        if (name == null) {
            try {
                logger.warn("Node " + n.getName() + " not marked");
                table.enter(n);
            } catch (Exception e) {
                throw new RuntimeException("Unable to enter scope for node " + n.getName());
            }
        } else {
            table.setScope(table.getScope(name));
        }
    }

    public static void exitScope(SymbolTable table, Node n) {
        table.exit(n);
    }

    public static void printNestedScopes(Scope scope) {
        logger.debug("Nested scopes for scope " + scope.getName());
        Iterator<String> it = scope.nested();
        while (it.hasNext()) {
            logger.debug("  " + it.next());
        }
    }

    public static void printNestedSymbols(Scope scope) {
        logger.debug("Nested symbols for scope " + scope.getName());
        Iterator<String> it = scope.symbols();
        while (it.hasNext()) {
            logger.debug("  " + it.next());
        }
    }

    // Add your methods here!

}