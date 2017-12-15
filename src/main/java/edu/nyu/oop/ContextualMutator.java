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
        String methodName = n.getString(2);

        visit(n);

        //System.out.println("check method: " + methodName);
        //System.out.println(n);

        // check whether it is System.out.print()/println()
        if (receiver != null &&
                receiver.getName().equals("SelectionExpression") &&
                receiver.getNode(0).getName().equals("PrimaryIdentifier") &&
                receiver.getNode(0).getString(0).equals("System") &&
                receiver.getString(1).equals("out"))
            return n;

        //method name mangling
        if (!"super".equals(methodName) && !"this".equals(methodName)) {
            // find type to search for relevant methods
            Type typeToSearch;
            if (receiver == null || "ThisExpression".equals(receiver.getName())) // receiver is of current class
                typeToSearch = JavaEntities.currentType(table);
            else if (TypeUtil.getType(receiver).hasAlias()) // receiver is of some other class
                typeToSearch = TypeUtil.getType(receiver).toAlias();
            else // for static method calls of the form A.m()
                typeToSearch = JavaEntities.qualifiedNameToType(
                        table, classpath(), table.current().getQualifiedName(), receiver.getString(0));

            // find type of called method
            List<Type> actuals = JavaEntities.typeList((List) dispatch(n.getNode(3)));
            MethodT method =
                    JavaEntities.typeDotMethod(table, classpath(), typeToSearch, true, methodName, actuals);

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

            // make this access explicit
            if (!TypeUtil.isStaticType(method)) {
                if (receiver == null)
                    n.set(0, makeThisExpression());
            }

            // mutate and wrap call expression
            Node replacement = GNode.create("CBlock");
            String temp_name = generate_temp_name(counter++);

            // A temp = translate(e);
            replacement.add(create_field_dec(
                    TypeResolver.createType(receiver_type_name, null), temp_name, n.getNode(0)));
            // __rt::checkNotNull(temp);
            Node primary_id = GNode.create("PrimaryIdentifier", temp_name);
            replacement.add(create_callexp(null, "__rt::checkNotNull",
                    GNode.create("Arguments", primary_id)) );

            // custom mutation based on method type
            if (TypeUtil.isStaticType(method)) {
                //__A::m();
                replacement.add(create_callexp(null,
                        "__"+receiver_type_name+"::"+new_name,
                        n.getNode(3)));
            } else if (TypeUtil.isPrivateType(method)) {
                //__A::m(temp);
                replacement.add(create_callexp(null,
                        "__"+receiver_type_name+"::"+new_name,
                        add_this_argu(n.getNode(3), primary_id)));
            } else {
                //temp->vptr->m(temp);
                replacement.add(create_callexp(add_vptr(primary_id),
                        new_name,
                        add_this_argu(n.getNode(3), primary_id)));
            }

            TypeUtil.setType(replacement, JavaEntities.currentType(table));

            return replacement;
        }

        return n;
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

    public String generate_temp_name(int x){
        String temp = "temp";
        temp = temp + Integer.toString(x);
        return temp;
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

    public Node create_castexp(Node receiver, String method_name, Node arguments){
        Node cast = GNode.create("NewCastExpression");
        cast.add(receiver);
        cast.add(null);
        cast.add(method_name);
        cast.add(arguments);
        return cast;
    }

    public Node visitCastExpression(GNode n){
        String cast_to=n.getNode(0).getNode(0).getString(0);
        Node cast_exp = create_castexp(null, "__rt::java_cast<"+cast_to+">",
                GNode.create("Arguments", n.getNode(1)));
        //System.out.println(cast_exp);
        TypeUtil.setType(cast_exp, JavaEntities.qualifiedNameToType(
                table, classpath(), table.current().getQualifiedName(), cast_to));
        return cast_exp;
    }

}
