package edu.nyu.oop.util;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import xtc.Constants;

import xtc.lang.JavaAnalyzer;
import xtc.lang.JavaAstSimplifier;
import xtc.lang.JavaEntities;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Attribute;
import xtc.util.SymbolTable;
import xtc.util.Runtime;
import xtc.type.*;


/*
 * A visitor that builds the symbol table.
 */
public class SymbolTableBuilder extends ContextualVisitor {

    public SymbolTableBuilder(Runtime runtime, SymbolTable table) {
        super(runtime, table);
    }

    public SymbolTableBuilder(final Runtime runtime) {
        super(runtime, new SymbolTable());
    }

    private void forceMark(Node n) {
        n.setProperty(Constants.SCOPE, table.current().getQualifiedName());
    }

    public void visitClassDeclaration(GNode n) {
        String className = n.getString(1);
        table.enter(className);
        forceMark(n);
        visit(n);
        table.exit();
    }

    public void visitBlock(GNode n) {
        table.enter(table.freshName("block"));
        forceMark(n);
        visit(n);
        table.exit();
    }

    public void visitBlockDeclaration(GNode n) {
        table.enter(table.freshName("block"));
        forceMark(n);
        visit(n);
        table.exit();
    }

    public void visitForStatement(GNode n) {
        table.enter(table.freshName("forStatement"));
        forceMark(n);
        visit(n);
        table.exit();
    }

    public void visit(GNode n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }

    public final List<Type> visitFieldDeclaration(final GNode n) {
        @SuppressWarnings("unchecked")
        final List<Attribute> modifiers = (List<Attribute>) dispatch(n.getNode(0));
        Type type = (Type) dispatch(n.getNode(1));
        return processDeclarators(modifiers, type, n.getGeneric(2));
    }

    public void visitBasicForControl(final GNode n) {
        @SuppressWarnings("unchecked")
        final List<Attribute> modifiers = (List<Attribute>) dispatch(n.getNode(0));
        Type type = (Type) dispatch(n.getNode(1));
        processDeclarators(modifiers, type, n.getGeneric(2));
        visit(n);
    }

    /**
     * Visit a Modifiers = Modifier*.
     */
    public final List<Attribute> visitModifiers(final GNode n) {
        final List<Attribute> result = new ArrayList<Attribute>();
        for (int i = 0; i < n.size(); i++) {
            final String name = n.getGeneric(i).getString(0);
            final Attribute modifier = JavaEntities.nameToModifier(name);
            if (null == modifier)
                runtime.error("unexpected modifier " + name, n);
            else if (result.contains(modifier))
                runtime.error("duplicate modifier " + name, n);
            else
                result.add(modifier);
        }
        return result;
    }

    public final List<Type> processDeclarators(final List<Attribute> modifiers,
                                               final Type type, final GNode declarators) {
        final List<Type> result = new ArrayList<Type>();
        boolean isLocal = JavaEntities.isScopeLocal(table.current().getQualifiedName());
        for (final Object i : declarators) {
            GNode declNode = (GNode) i;
            String name = declNode.getString(0);
            Type dimType = JavaEntities.typeWithDimensions(type,
                countDimensions(declNode.getGeneric(1)));
            Type entity = isLocal ? VariableT.newLocal(dimType, name) :
                VariableT.newField(dimType, name);
            for (Attribute mod : modifiers)
                entity.addAttribute(mod);
            if (null == table.current().lookupLocally(name)) {
                result.add(entity);
                table.current().define(name, entity);
                //entity.scope(table.current().getQualifiedName());
            }
        }
        return result;
    };

    public final Type visitPrimitiveType(final GNode n) {
        final Type result = JavaEntities.nameToBaseType(n.getString(0));
        return result;
    }

    public final Type visitType(final GNode n) {
        final boolean composite = n.getGeneric(0).hasName("QualifiedIdentifier");
        final Object dispatched0 = dispatch(n.getNode(0));
        assert dispatched0 != null;
        final Type componentT = composite ? new AliasT((String) dispatched0) : (Type) dispatched0;
        final int dimensions = countDimensions(n.getGeneric(1));
        final Type result = JavaEntities.typeWithDimensions(componentT, dimensions);
        final Type resolved = JavaEntities.resolveIfAlias(table, classpath(), table.current().getQualifiedName(), result);
        if (null == resolved || resolved.isAlias() && null == resolved.toAlias().getType()) {
            if (null != n)
                runtime.error("unknown class or interface " + result.toAlias().getName(), n);
            return ErrorT.TYPE;
        }
        return resolved;
    }

    public static int countDimensions(final GNode dimNode) {
        return null == dimNode ? 0 : dimNode.size();
    }

    public final Type visitVoidType(final GNode n) {
        return JavaEntities.nameToBaseType("void");
    }

    public final String visitQualifiedIdentifier(final GNode n) {
        final StringBuffer b = new StringBuffer();
        for (int i = 0; i < n.size(); i++) {
            if (b.length() > 0)
                b.append(Constants.QUALIFIER);
            b.append(n.getString(i));
        }
        return b.toString();
    }

    public SymbolTable getTable(Node n) {
        new JavaAstSimplifier().dispatch(n);
        new JavaAnalyzer(runtime, table).dispatch(n);
        dispatch(n);
        //table.current().dump(runtime.console());
        //runtime.console().flush();
        return table;
    }

}
