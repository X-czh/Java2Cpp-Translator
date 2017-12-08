package edu.nyu.oop;

import edu.nyu.oop.util.ContextualVisitor;
import edu.nyu.oop.util.TypeUtil;
//import sun.nio.fs.GnomeFileTypeDetector;
import xtc.Constants;
import xtc.lang.JavaEntities;
import xtc.tree.Attribute;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.type.ClassOrInterfaceT;
import xtc.type.MethodT;
import xtc.type.Type;
import xtc.type.VariableT;
import xtc.util.Runtime;
import xtc.util.SymbolTable;
import java.util.ArrayList;
import java.util.List;

public class ContextualMutator extends ContextualVisitor {
    private SymbolTable table;

    public ContextualMutator(Runtime runtime, SymbolTable table) {
        super(runtime, table);
        this.table = table;
    }

    public void mutate(List<Node> javaAstList) {
        for (Node tree : javaAstList)
            super.dispatch(tree);
    }

    public Node visitCallExpression(GNode n) {
        Node receiver = n.getNode(0);
        String methodName = n.getString(2);
        dispatch(n.getNode(0));
        dispatch(n.getNode(3));

        System.out.println("find method "+methodName+":");
        // check whether it is System.out.print()/println()
        if (receiver != null &&
                n.getNode(0).getName().equals("SelectionExpression") &&
                n.getNode(0).getNode(0).getName().equals("PrimaryIdentifier") &&
                n.getNode(0).getNode(0).getString(0).equals("System") &&
                n.getNode(0).getString(1).equals("out")) {
            GNode printingExpression = GNode.create("PrintingExpression");
            printingExpression.add(n.getNode(3).getNode(0));
            printingExpression.add(n.getString(2));
            return printingExpression;
        }
        //TODO
        //add explicit this
        if (!"super".equals(methodName) && !"this".equals(methodName)) {
            if (receiver == null || "ThisExpression".equals(receiver.getName())) {
                // find type to search for relevant methods
                Type typeToSearch = JavaEntities.currentType(table);

                // find type of called method
                List<Type> actuals = JavaEntities.typeList((List) dispatch(n.getNode(3)));
                MethodT method =
                        JavaEntities.typeDotMethod(table, classpath(), typeToSearch, true, methodName, actuals);

                if (method != null) {
                    System.out.println("resolve method " + methodName + ":");

                    List<Type> param_use = method.getParameters();
                    String new_name = methodName;
                    for (int i = 0; i < param_use.size(); i++) {
                        System.out.println(param_use.get(i).toAlias().getName());
                        new_name = new_name + "_" + param_use.get(i).toAlias().getName();
                    }
                    n.set(2, new_name);
                }

                if (!TypeUtil.isStaticType(method)) {
                    n.set(3, addExplicitThisArgument(n.getNode(3)));
                    if (receiver == null)
                        n.set(0, makeThisExpression()); // make 'this' access explicit
                    if (!TypeUtil.isPrivateType(method)) {
                        GNode n1 = GNode.create("SelectionExpression", n.getNode(0), "__vptr");
                        n.set(0, n1);
                    }
                }
            } else {
                Type typeToSearch = TypeUtil.getType(receiver).toAlias();
                System.out.println(typeToSearch);

                List<Type> actuals = JavaEntities.typeList((List) dispatch(n.getNode(3)));
                MethodT method =
                        JavaEntities.typeDotMethod(table, classpath(), typeToSearch, true, methodName, actuals);

                if (method != null) {
                    System.out.println("resolve method " + methodName + ":");

                    List<Type> param_use = method.getParameters();
                    String new_name = methodName;
                    for (int i = 0; i < param_use.size(); i++) {
                        System.out.println(param_use.get(i).toAlias().getName());
                        new_name = new_name + "_" + param_use.get(i).toAlias().getName();
                    }
                    n.set(2, new_name);
                }

                if (!TypeUtil.isStaticType(method)) {
                    n.set(3, addExplicitThisArgument(n.getNode(3)));
                    if (receiver == null)
                        n.set(0, makeThisExpression()); // make 'this' access explicit
                    if (!TypeUtil.isPrivateType(method)) {
                        GNode n1 = GNode.create("SelectionExpression", n.getNode(0), "__vptr");
                        n.set(0, n1);
                    }
                }
            }
        }

        return n;
    }

    public Node visitPrimaryIdentifier(GNode n) {
        String fieldName = n.getString(0);

        // find type to search for relevant fields
        ClassOrInterfaceT typeToSearch = JavaEntities.currentType(table);
        if (typeToSearch == null) return n;

        // find type of
        VariableT field = null;
        SymbolTable.Scope oldScope = table.current();
        JavaEntities.enterScopeByQualifiedName(table, typeToSearch.getScope());
        for (final VariableT f : JavaEntities.fieldsOwnAndInherited(table, classpath(), typeToSearch))
            if (f.getName().equals(fieldName)) {
                field = f;
                break;
            }
        table.setScope(oldScope);

        if (field == null) return n;

        // make this access explicit
        Type t = (Type) table.lookup(fieldName);
        if (t == null || !t.isVariable()) {
            t = field;
        }

        if (JavaEntities.isFieldT(t) && !TypeUtil.isStaticType(t)) {
            GNode n1 = GNode.create("SelectionExpression", makeThisExpression(), fieldName);
            TypeUtil.setType(n1, TypeUtil.getType(n));
            return n1;
        }

        return n;
    }

    public List<Type> visitArguments(final GNode n) {
        List<Type> result = new ArrayList<Type>(n.size());
        for (int i = 0; i < n.size(); i++) {
            GNode argi = n.getGeneric(i);
            Type ti = (Type) argi.getProperty(Constants.TYPE);
            if (ti.isVariable()) {
                VariableT vi = ti.toVariable();
                ti = vi.getType();
            }
            result.add(ti);
            Object argi1 = dispatch(argi);
            if (argi1 != null && argi1 instanceof Node) {
                n.set(i, argi1);
            }
        }
        return result;
    }

    public void visit(GNode n) {
        for (int i = 0; i < n.size(); ++i) {
            Object o = n.get(i);
            if (o instanceof Node) {
                Object o1 = dispatch((Node) o);
                if (o1 != null && o1 instanceof Node) {
                    n.set(i, o1);
                }
            }
        }
    }

    private GNode makeThisExpression() {
        GNode _this = GNode.create("ThisExpression", null);
        TypeUtil.setType(_this, JavaEntities.currentType(table));
        return _this;
    }

    private GNode addExplicitThisArgument(Node n) {
        GNode arguments = GNode.create("Arguments");

        GNode explicitThisArg = GNode.create("Argument");
        explicitThisArg.add(GNode.create("PrimaryIdentifier", "__this"));
        arguments.add(explicitThisArg);

        for (Object o : n)
            arguments.add(o);

        return arguments;
    }

}
