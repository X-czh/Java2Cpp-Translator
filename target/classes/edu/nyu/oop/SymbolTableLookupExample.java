package edu.nyu.oop;

import edu.nyu.oop.util.ContextualVisitor;
import edu.nyu.oop.util.SymbolTableUtil;
import org.slf4j.Logger;
import xtc.lang.JavaEntities;

import xtc.Constants;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.util.SymbolTable;
import xtc.util.Runtime;
import xtc.type.*;
import xtc.type.Type.Tag;

import java.util.*;

public class SymbolTableLookupExample extends ContextualVisitor {
    final private Map<String, Tag> summary = new HashMap<>();


    public SymbolTableLookupExample(Runtime runtime, SymbolTable table) {
        super(runtime, table);
    }

    public void visitMethodDeclaration(GNode n) {
        SymbolTableUtil.enterScope(table, n);

        summary.put(n.getString(3)+"()", Tag.FUNCTION);

        // Extract a list representing the parameters to this method.
        List<VariableT> params = SymbolTableUtil.extractFormalParams(table.current());
        for(VariableT p : params) {
            summary.put(p.getName(), p.tag());
        }

        visit(n);
        SymbolTableUtil.exitScope(table, n);
    }

    public void visitPrimaryIdentifier(GNode n) {
        String name = n.getString(0);

        if (table.current().isDefined(name)) {
            Type type = (Type) table.current().lookup(name);
            runtime.console().loc(n).pln();
            summary.put(name, type.tag());
            if (JavaEntities.isLocalT(type))
                runtime.console().p("Found occurrence of local variable ").p(name).p(" with type ").pln(type.toString());
            else if (JavaEntities.isFieldT(type))
                runtime.console().p("Found occurrence of field ").p(name).p(" with type ").pln(type.toString());

        }
    }

    public Map<String, Tag> getSummary(Node n) {
        this.dispatch(n);
        return this.summary;
    }

    public void printSummary() {
        runtime.console().pln("printSummary()");
        Set<String> keys = summary.keySet();
        for(String k : keys) {
            runtime.console().pln("  " + k + ":" + summary.get(k));
        }
        runtime.console().flush();
    }

}