package edu.nyu.oop.util;

import xtc.Constants;
import xtc.lang.JavaEntities;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.type.*;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * A visitor that keeps track of the current scope in the symbol table.
 * Assumes that the given symbol table has been constructed using the SymbolTableBuilder.
 */
public class ContextualVisitor extends Visitor {

    protected SymbolTable table;
    protected Runtime runtime;

    public final List<File> classpath() {
        return JavaEntities.classpath(runtime);
    }

    public ContextualVisitor(Runtime runtime, SymbolTable table) {
        this.runtime = runtime;
        this.table = table;
    }

    public void visitCompilationUnit(GNode n) {
        String packageScope = null == n.get(0) ? visitPackageDeclaration(null) : (String) dispatch(n.getNode(0));
        table.enter(packageScope);
        table.enter(n);
        table.mark(n);

        for (int i = 1; i < n.size(); i++) {
            GNode child = n.getGeneric(i);
            dispatch(child);
        }

        table.exit();
        table.exit();
        table.setScope(table.root());
    }

    public String visitPackageDeclaration(GNode n) {
        String canonicalName = null == n ? "" : (String) dispatch(n.getNode(1));
        final PackageT result = JavaEntities.canonicalNameToPackage(table, canonicalName);
        return JavaEntities.packageNameToScopeName(result.getName());
    }

    public void visitClassDeclaration(GNode n) {
        SymbolTableUtil.enterScope(table, n);
        table.mark(n);
        visit(n);
        SymbolTableUtil.exitScope(table, n);
    }

    public void visitMethodDeclaration(GNode n) {
        SymbolTableUtil.enterScope(table, n);
        table.mark(n);
        visit(n);
        SymbolTableUtil.exitScope(table, n);
    }

    public void visitBlockDeclaration(GNode n) {
        SymbolTableUtil.enterScope(table, n);
        table.mark(n);
        visit(n);
        SymbolTableUtil.exitScope(table, n);
    }


    public void visitBlock(GNode n) {
        SymbolTableUtil.enterScope(table, n);
        table.mark(n);
        visit(n);
        SymbolTableUtil.exitScope(table, n);
    }

    public void visitForStatement(GNode n) {
        SymbolTableUtil.enterScope(table, n);
        table.mark(n);
        visit(n);
        SymbolTableUtil.exitScope(table, n);
    }

    /**
     * Visit a QualifiedIdentifier = Identifier+.
     */
    public String visitQualifiedIdentifier(final GNode n) {
        // using StringBuffer instead of Utilities.qualify() to avoid O(n^2)
        // behavior
        final StringBuffer b = new StringBuffer();
        for (int i = 0; i < n.size(); i++) {
            if (b.length() > 0)
                b.append(Constants.QUALIFIER);
            b.append(n.getString(i));
        }
        return b.toString();
    }

    public void visit(GNode n) {
        for (Object o : n)
            if (o instanceof Node) dispatch((Node) o);
    }

}