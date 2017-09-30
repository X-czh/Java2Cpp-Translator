package edu.nyu.oop;

import java.util.Iterator;

import xtc.tree.Printer;
import xtc.util.SymbolTable;
import xtc.util.SymbolTable.Scope;
import xtc.util.Runtime;
import xtc.type.Type;
import xtc.type.TypePrinter;

public class SymbolTablePrinter {
    private SymbolTable table;
    private Runtime runtime;

    public SymbolTablePrinter(Runtime runtime, SymbolTable table) {
        this.runtime = runtime;
        this.table = table;
    }

    public void full() {
        table.current().dump(runtime.console());
        runtime.console().flush();
    }

    public void simple() {
        Scope scope = table.current();
        simple(scope, runtime.console(), new TypePrinter(runtime.console()));
        table.enter(scope.getName());
    }

    private void simple(Scope scope, Printer printer, TypePrinter typePrinter) {
        printer.indent();
        printer.p("scope: ").pln(scope.getName());
        printer.incr();

        // Print symbols in current scope
        for (Iterator<String> iter = scope.symbols(); iter.hasNext(); ) {
            String sym = iter.next();
            printer.indent();

            printer.p("symbol: ").p(sym);

            Object value = scope.lookupLocally(sym);
            if (null == value) continue;

            if (value instanceof Type) {
                printer.p(" -> ");
                printer.incr();
                try {
                    typePrinter.dispatch((Type) value);
                } catch (Exception e) {}
                printer.decr();
                printer.pln();
            }
            printer.flush();
        }

        // Recurse into all nested scopes of all current scopes
        for (Iterator<String> iter = scope.nested(); iter.hasNext(); ) {
            String nestedScopeName = iter.next();
            Scope nestedScope = scope.getNested(nestedScopeName);
            table.enter(nestedScopeName);
            try {
                simple(nestedScope, printer, typePrinter);
            } catch (Exception e) { }
            table.exit();
        }

        printer.decr();
    }
}
