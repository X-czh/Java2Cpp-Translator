package edu.nyu.oop;

import java.io.*;

import edu.nyu.oop.util.RecursiveVisitor;
import edu.nyu.oop.util.XtcProps;
import org.slf4j.Logger;
import xtc.tree.*;

/**
 * This class demonstrates a trivial usage of XTC's Printer class.
 * For much more sophisticated printing, see xtc.lang.CPrinter
 */
public class CppPrinter extends RecursiveVisitor {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    static int counter = 0;

    private static int flag = -1;

    private Printer printer;

    private static int namespaceNum=0;

    private String outputLocation = XtcProps.get("output.location");

    public CppPrinter(String outputFile) {
        Writer w = null;
        try {
            FileOutputStream fos = new FileOutputStream(outputLocation + outputFile);
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }

        // Register the visitor as being associated with this printer.
        // We do this so we get some nice convenience methods on the printer,
        // such as "dispatch", You should read the code for Printer to learn more.
        printer.register(this);
    }

    public void printHeader(Node source) {
        flag=0;
        headerHeadOfFile();
        visit(source);
        printer.flush(); // important!
    }

    public void printCpp(Node source){
        flag=1;
        cppHeadOfFile();
        visit(source);
        printer.flush();
    }

    public void printMain(Node source){
        flag=2;
        mainHeadOfFile();
        visit(source);
        printer.flush();
    }

    private void cout(String line) {
        printer.incr().indent().pln("cout << \"" + line + "\" << endl;").decr();
    }

    private void headerHeadOfFile() {
        printer.pln("#pragma once");
        printer.pln();
        printer.pln("#include \"java_lang.h\"");
        printer.pln();
        printer.pln("using namespace java::lang;");
        printer.pln();
    }

    private void cppHeadOfFile(){
        printer.pln("#include \"output.h\"");
        printer.pln("#include <iostream>");
        printer.pln();
        printer.pln("using namespace java::lang;");
        printer.pln("using namespace std;");
    }

    private void mainHeadOfFile(){
        printer.pln("#include <iostream>");
        printer.pln();
        printer.pln("#include \"ptr.h\"");
        printer.pln("#include \"java_lang.h\"");
        printer.pln("#include \"output.h\"");
        printer.pln();
        printer.pln("using namespace java::lang;");
        printer.pln("using namespace std;");
    }

    public void visitCompilationUnit(GNode source){visit(source);}

    public void visitNamespaceDeclaration(GNode source) {

        String namespace = source.getString(0);
        printer.indent().incr().p("namespace " + namespace);
        printer.pln(" {").pln();

        //continue to visit sub-nodes
        visit(source);

        printer.decr().indent().pln("}").pln();
    }

    public void visitForwardDeclarations(GNode source){
        visit(source);
    }

    public void visitForwardDeclaration(GNode source) {
        String type = source.getString(0);
        String name = source.getString(1);
        printer.indent().p(type).pln(" "+name+";").pln();
    }

    public void visitTypeSpecifiers(GNode source){
        visit(source);
    }

    public void visitTypeSpecifier(GNode source){
        String types = source.getString(0);
        String systemType = source.getString(1);
        String CustomType = source.getString(2);
        printer.indent().pln(types+" "+systemType+" "+CustomType+";").pln();
    }


    public void visitClassDeclaration(GNode source){
        //get class name and class modifiers
        Node classModifiers = source.getNode(0);
        String className = source.getString(1);

        //get class body info
        //Node classBody = source.getNode(5);

        //print class dec
        printer.indent().pln("struct "+className+" {").pln().incr();

        //visit source
        visit(source);

        //close the parentheses
        printer.decr().indent().pln("};").pln();
    }

    public void visitClassBody(GNode source){
        visit(source);
    }

    public void visitFieldDeclarations(GNode source){
        visit(source);
    }

    public void visitFieldDeclaration(GNode source){
        //get Field info
        Node modifiers = source.getNode(0);
        String type = TypeResolver.typeToString(source.getNode(1));
        Node declarators = source.getNode(2);

        printer.indent();

        if(modifiers!=null && modifiers.getName().compareTo("Modifiers")==0) dispatch(modifiers);

        printer.p(type+" ");

        for(int i=0;i<declarators.size();i++){
            Node declarator = declarators.getNode(i);
            String declaratorName = declarator.getString(0);
            printer.p(declaratorName);
            if (i<declarators.size()-1) printer.p(",");
        }

        printer.pln(";");
        printer.pln();
    }

    public void visitConstructorDeclaration(GNode source){

        String className = source.getString(2);
        printer.indent();
        printer.p(className+"(");

        //Formal parameters
        Node formalParameters = source.getNode(3);

        if (formalParameters!=null && formalParameters.getName().compareTo("FormalParameters")==0) dispatch(formalParameters);
        //there is no Formal parameters
        else printer.p(" )");

        //Initializations

        Node initializations = source.getNode(4);
        if(initializations!=null && initializations.getName().compareTo("Initializations")==0){
            printer.pln().indent().p(":");
            for (int i=0; i<initializations.size();i++){
                Node initialization = initializations.getNode(i);

                printer.p(initialization.getString(0)+"("+initialization.getString(1)+")");

                if (initializations.size()>=1 && i<initializations.size()-1){
                    printer.p(",").pln().indent();
                }

            }
        }

        // last node block
        Node block = source.getNode(5);
        if (block!=null && block.getName().compareTo("Block") == 0 && block.size()!=0){
            //to be implemented in later phase
            printer.p(" {").pln();
            visit(block);
            printer.indent().p("}").pln();
        }

        else {
            printer.pln("{}");
        }

        printer.pln();
    }

    public void visitFormalParameters(GNode source){

        int parameterSize = source.size();

        if(parameterSize==0) printer.pln(")");

        for(int i=0;i<parameterSize;i++){
            Node formalParameter = source.getNode(i);
            //traverse modifiers and type
            visit(formalParameter);
            //output parameterName
            String parameterName = formalParameter.getString(3);
            printer.p(parameterName);
            if(i<parameterSize-1) printer.p(",");
            else printer.p(")");
        }
    }

    public void visitType(GNode source){
        String type = TypeResolver.typeToString(source);
        //String type = source.getNode(0).getString(0);
        printer.p(type+" ");
        //traverse on dimension
        //visit(source);
    }

    /*
    public void visitDimensions(GNode source){
        for(int i=0;i<source.size();i++){
            String dimension = source.getString(i);
            printer.p("[] ");
        }
    }
    */


    public void visitModifiers(GNode source){
        if(flag==0) visit(source);
    }

    public void visitModifier(GNode source){
        if(source.getString(0).equals("static")) printer.p(source.getString(0)+" ");
    }


    public void visitMethodDeclaration(GNode source){

        printer.indent();

        //traverse down modifiers node
        Node modifiers = source.getNode(0);
        if(modifiers!=null && modifiers.getName().compareTo("Modifiers")==0) dispatch(modifiers);

        String returnType = TypeResolver.typeToString(source.getNode(2));
        String methodName = source.getString(3);

        //printing returnType and the left parenthesis
        printer.p(returnType+" ");
        printer.p(methodName+"(");

        Node formalParameters = source.getNode(4);
        Node block = source.getNode(7);

        if(formalParameters!=null && formalParameters.getName().compareTo("FormalParameters")==0 ) dispatch(formalParameters);
        //else there is no parameter
        else printer.p(" )");

        if(block!=null && block.getName().compareTo("Block")==0 && block.size()!=0){
            //print what is inside the block
            //not yet to be implemented in headerfile printing
            printer.pln("{");
            visit(block);
        }

        else {
            printer.pln("{}");
        }
    }

    //post midterm stuffs
    public void visitConditionalStatement(GNode source){
        visit(source);
    }

    public void visitReturnStatement(GNode source){
        printer.p("Return ");
        visit(source);
    }

    public void visitStringLiteral(GNode source){
        String literal=source.getString(0);
        literal="__rt::literal("+literal+")";
        printer.pln(literal+";");
    }

    public void visitIntegerLiteral(GNode source){
        String literal=source.getString(0);
        literal="__rt::literal("+literal+")";
        printer.pln(literal+";");
    }

    public void visitFloatLiteral(GNode source){
        String literal=source.getString(0);
        literal="__rt::literal("+literal+")";
        printer.pln(literal+";");
    }

    public void visitForStatement(GNode source){
    Node basic = source.getNode(0);
    printer.p("for(");
    visit(basic);
    Node block = source.getNode(1);
    visit(block);
    }

    public void visitWhileStatement(GNode source){
        visit(source);
    }

    public void visitBlock(GNode source){
        visit(source);
    }

    public void visitSelectionExpression(GNode source){
        Node primaryIdentifier = source.getNode(0);
        String caller = primaryIdentifier.getString(0);
        String field = source.getString(1);
        printer.p(caller+"."+field);
    }

    //to be completed
    public void visitExpressionStatement(GNode source){
        visit(source);
    }

    public void visitExpression(GNode source){
        String k = source.getNode(0).getString(0);
        String equal = source.getString(1);
        Node operation = source.getNode(2);
        String K = operation.getNode(0).getString(0);
        String operator = operation.getString(1);
        String literal = operation.getNode(2).getString(0);

        printer.pln(k+" "+equal+" "+K +" "+operator+" "+literal+" ;");
    }

    public void visitPrintingExpression(GNode source){
        String printType = source.getString(1);
        printer.p("cout<< ");
        visit(source);
        if(printType.equals("println")){
            printer.pln("<<endl;");
        }
    }

    public void visitMainMethodDefinition(GNode source){
        String mainMethodLocation = source.getString(0);
        printer.pln("int main(int argc, char* argv[]) {");
            // Implement generic interface between C++'s main function and Java's main function
        printer.pln("__rt::Array<String> args = new __rt::__Array<String>(argc - 1);");
        printer.pln();
        printer.pln("for (int32_t i = 1; i < argc; i++) {");
        printer.pln("(*args)[i] = __rt::literal(argv[i]);");
        printer.pln("}");
        printer.pln();
        printer.pln(mainMethodLocation+"::main(args);");
        printer.pln();
        printer.pln("return 0;");
        printer.pln("}");

    }

    //fix
    public void visitClassMethodDefinition(GNode source){
        String name = source.getString(0);
        String parent = source.getString(1);
        String __name = "__"+name.substring(name.lastIndexOf(".") + 1, name.length());
        String __parent = "__"+parent;

        printer.pln("Class "+__name+"::__class() {");
        printer.pln("static Class k =");
        printer.pln("new __Class(__rt::literal(\""+name+"\"), java::lang::"+__parent+"::__class());");
        printer.pln("return k;");
        printer.pln("}");
    }

    public void visitDeclarator(GNode source){
        Node declarator = source.getNode(0);
        String string_i = declarator.getString(0);
        String string_integer = declarator.getNode(2).getString(0);
        printer.p(string_i+" = "+string_integer+"; ");
    }

    public void visitRelationalExpression(GNode source){
        Node primaryIndentifier = source.getNode(0);
        String string_i = primaryIndentifier.getString(0);

        String compare = primaryIndentifier.getString(1);
        Node selectionExpression = source.getNode(2);
        String s = selectionExpression.getNode(0).getString(0);
        String l = selectionExpression.getString(1);
        printer.p(string_i+" "+compare+" "+s+"."+l+"; ");
    }

    public void visitExpressionList(GNode source){
        visit(source);
    }

    public void visitPostfixExpression(GNode source){
        String string_i = source.getNode(0).getString(0);
        String operation = source.getString(1);
        printer.pln(string_i+operation+")");
    }

    public void visitPrefixExpression(GNode source){
        String string_i = source.getNode(0).getString(0);
        String operation = source.getString(1);
        printer.pln(operation+string_i+")");
    }


    public String generate_temp_name(int x){
        String temp = "temp";
        temp = temp + Integer.toString(x);
        return temp;
    }

    public void visitCallExpression(Node n){
        printer.indent();
        printer.p("({ ");
        String temp_name = generate_temp_name(counter++);
        boolean is_virtual = "VptrSelectionExpression".equals(n.getNode(0).getName());
        printer.p("auto " + temp_name + " = ");
        if (is_virtual) {
            visit(n.getNode(0).getNode(0));
        }
        else {
            visit(n.getNode(0));
        }
        printer.pln(";");
        printer.incr().indent().pln("__rt::checkNotNull(" + temp_name + ");");
        printer.indent().p(temp_name);
        if (is_virtual) printer.p("->__vptr");
        printer.p("->"+n.getString(2)+"(" + temp_name);
        for (int i=0; i<n.getNode(3).size(); i++) {
            printer.p(",");
            visit(n.getNode(3).getNode(i));
        }
        printer.pln(");");
        printer.decr().indent().pln("})");
    }

}

