package edu.nyu.oop;

import edu.nyu.oop.util.ContextualVisitor;
import edu.nyu.oop.util.TypeUtil;
import xtc.Constants;
import xtc.lang.JavaEntities;
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
import java.util.Arrays;

/**
 * This class mutates the input list of Java ASTs and outputs the modified Java ASTs
 * with method overloading resolved statically by method name mangling and dynamic
 * dispatch through virtual tables supported via identifying statically whether a
 * called method is virtual or not.
 */
public class ContextualMutator extends ContextualVisitor {

    static int counter = 0;
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
        Node argus = n.getNode(3);
        String methodName = n.getString(2);

        if (methodName.equals("__init")) return n;

        System.out.println("resolving method :"+methodName);
        // check whether it is System.out.print()/println()
        if (receiver != null &&
                receiver.getName().equals("SelectionExpression") &&
                receiver.getNode(0).getName().equals("PrimaryIdentifier") &&
                receiver.getNode(0).getString(0).equals("System") &&
                receiver.getString(1).equals("out")) {
            visit(n);
            GNode printingExpression = GNode.create("PrintingExpression");
            printingExpression.add(n.getNode(3).getNode(0));
            printingExpression.add(n.getString(2));
            return printingExpression;
        }

        //method name mangling
        if (!"super".equals(methodName) && !"this".equals(methodName)) {
            // find type to search for relevant methods
            Type typeToSearch;
            System.out.println(TypeUtil.getType(receiver));
            if (receiver == null || "ThisExpression".equals(receiver.getName())) // receiver is of current class
                typeToSearch = JavaEntities.currentType(table);
            else if (TypeUtil.getType(receiver).hasAlias()) // receiver is of some other class
                typeToSearch = TypeUtil.getType(receiver).toAlias();
            else if (TypeUtil.getType(receiver).isClass()){
                typeToSearch = TypeUtil.getType(receiver);
            }
            else // for static method calls of the form A.m()
                typeToSearch = JavaEntities.qualifiedNameToType(
                        table, classpath(), table.current().getQualifiedName(), receiver.getString(0));

            // find type of called method
            List<Type> actuals = JavaEntities.typeList((List) dispatch(argus));

            visit(n);

            MethodT method =
                    JavaEntities.typeDotMethod(table, classpath(), typeToSearch, true, methodName, actuals);


            System.out.println(typeToSearch);
            System.out.println(actuals);

            if (method == null) return n;

            // extract type of receiver
            String receiver_type_name;
            if (typeToSearch.hasAlias())
                receiver_type_name = typeToSearch.toAlias().getName();
            else
                receiver_type_name = typeToSearch.toClass().getName();

            // extract param types to generate new name
            List<Type> param_use = method.getParameters();
            String new_name = methodName;
            for (int i = 0; i < param_use.size(); i++) {
                String temp;
                if (param_use.get(i).hasAlias())
                    temp = param_use.get(i).toAlias().getName();
                else
                    temp = param_use.get(i).toVariable().getType().toString();
                new_name = new_name + "_" + temp;
            }

            System.out.println("new name is: " + new_name);
            // make this access explicit
            if (!TypeUtil.isStaticType(method)) {
                if (receiver == null)
                    n.set(0, makeThisExpression());
            }


            // mutate and wrap call expression
            Node replacement = GNode.create("CBlock");
            String temp_name = generate_temp_name(counter++);

            if (TypeUtil.getType(receiver).hasAlias() || TypeUtil.getType(receiver).isClass()) {
                // A temp = translate(e);
                replacement.add(create_field_dec(
                        TypeResolver.createType(receiver_type_name, null), temp_name, n.getNode(0)));
                // __rt::checkNotNull(temp);
                Node primary_id = GNode.create("PrimaryIdentifier", temp_name);
                replacement.add(create_callexp(null, "__rt::checkNotNull",
                        GNode.create("Arguments", primary_id)));

                // custom mutation based on method type
                if (TypeUtil.isStaticType(method)) {
                    //__A::m();
                    replacement.add(create_staticcallexp(GNode.create("PrimaryIdentifier", receiver_type_name),
                            new_name,
                            n.getNode(3)));
                } else if (TypeUtil.isPrivateType(method)) {
                    //__A::m(temp);
                    replacement.add(create_staticcallexp(GNode.create("PrimaryIdentifier", receiver_type_name),
                            new_name,
                            add_this_argu(n.getNode(3), primary_id)));
                } else {
                    //temp->vptr->m(temp);
                    replacement.add(create_callexp(add_vptr(primary_id),
                            new_name,
                            add_this_argu(n.getNode(3), primary_id)));
                }
            } else {
                //__A::m();
                replacement.add(create_staticcallexp(GNode.create("PrimaryIdentifier", receiver_type_name),
                        new_name,
                        n.getNode(3)));
            }



            TypeUtil.setType(replacement, JavaEntities.currentType(table));

            return replacement;

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

    public Node visitCastExpression(GNode n){
        visit(n);
        String cast_to=n.getNode(0).getNode(0).getString(0);
        Node cast_exp = create_castexp(null, "__rt::java_cast<"+cast_to+">",
                GNode.create("Arguments", n.getNode(1)));
        //System.out.println(cast_exp);
        TypeUtil.setType(cast_exp, JavaEntities.qualifiedNameToType(
                table, classpath(), table.current().getQualifiedName(), cast_to));
        return cast_exp;
    }

    public Node visitSelectionExpression(GNode n) {
        Node owner = n.getNode(0);
        Type tp = TypeUtil.getType(owner);
        if (tp.isAnnotated())
            return GNode.create("StaticSelectionExpression", owner, n.get(1));
        return n;
    }

    public Node visitCBlock(GNode n){
        return n;
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

    public Node create_castexp(Node receiver, String method_name, Node arguments){
        Node cast = GNode.create("NewCastExpression");
        cast.add(receiver);
        cast.add(null);
        cast.add(method_name);
        cast.add(arguments);
        return cast;
    }


    public Node add_vptr(Node p){
        Node selection = GNode.create("SelectionExpression", p, "__vptr");
        return selection;
    }

    public Node add_this_argu(Node argus, Node this_argu){
        Node new_argus = GNode.create("Arguments");
        new_argus.add(this_argu);
        for (int i=0; i<argus.size(); i++) {
            new_argus.add(argus.getNode(i));
        }
        return new_argus;
    }

    public Node create_field_dec(Node type, String name, Node thing){
        Node field = GNode.create("FieldDeclaration");
        field.add(GNode.create("Modifiers"));
        field.add(type);
        Node decs = GNode.create("Declarators");
        Node dec = GNode.create("Declarator", name, null, thing);
        decs.add(dec);
        field.add(decs);
        return field;
    }

    public Node create_callexp(Node receiver, String method_name, Node arguments){
        Node call = GNode.create("CallExpression");
        call.add(receiver);
        call.add(null);
        call.add(method_name);
        call.add(arguments);
        return call;
    }

    public Node create_staticcallexp(Node receiver, String method_name, Node arguments){
        Node call = GNode.create("StaticCallExpression");
        call.add(receiver);
        call.add(null);
        call.add(method_name);
        call.add(arguments);
        return call;
    }

    public String generate_temp_name(int x){
        String temp = "temp";
        temp = temp + Integer.toString(x);
        return temp;
    }

    public Node visitNewArrayExpression(GNode n){
        String temp_name = generate_temp_name(counter++);
        Node new_array = GNode.create("CBlock");
        Node array_type = GNode.create("Type");
        array_type.add(n.getNode(0));
        Node dimension = GNode.create("Dimensions");
        for (int i=0; i<n.getNode(1).size(); i++){
            dimension.add("[");
        }
        array_type.add(dimension);
        new_array.add(create_field_dec(array_type, temp_name, n));
        new_array.add(GNode.create("PrimaryIdentifier", temp_name+";"));
        return new_array;
    }

    public Node visitExpression(GNode n){
        visit(n);
        if ("=".equals(n.getString(1)) && "SubscriptExpression".equals(n.getNode(0).getName())) {
            Node array_store = GNode.create("CBlock");
            array_store.add(create_callexp(null, "__rt::checkStore",
                    GNode.create("Arguments",
                            GNode.create("PrimaryIdentifier", n.getNode(0).getNode(0).getString(0)),
                            n.getNode(2))));
            array_store.add(n);
            dispatch(array_store);
            return array_store;
        }
        return n;
    }

}
