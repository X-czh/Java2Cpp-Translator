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
        headOfFile();
        visit(source);
        printer.flush(); // important!
    }

    private void cout(String line) {
        printer.incr().indent().pln("cout << \"" + line + "\" << endl;").decr();
    }

    private void headOfFile() {
        printer.pln("#pragma once");
        printer.pln();
        printer.pln("#include \"java_lang.h\"");
        printer.pln();
        printer.pln("using namespace java::lang;");
        printer.pln();
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
        if (block!=null && block.getName().compareTo("Block") == 0){
            //to be implemented in later phase
            printer.p(" {").pln();
            visit(block);
            printer.indent().p("}").pln();
        }

        else {
            printer.pln(";");
        }

        printer.pln();
    }

    public void visitFormalParameters(GNode source){

        int parameterSize = source.size();

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
        String type = TypeResolver.typeToString(source.getNode(0).getNode(0));
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
        visit(source);
    }

    public void visitModifier(GNode source){
        printer.p(source.getString(0)+" ");
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

        if(block!=null && block.getName().compareTo("Block")==0){
            //print what is inside the block
            //not yet to be implemented in headerfile printing
        }

        else {
            printer.pln(";").pln();
        }
    }
}

