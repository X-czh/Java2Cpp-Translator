package edu.nyu.oop;

import java.io.*;

import edu.nyu.oop.util.XtcProps;
import org.slf4j.Logger;
import xtc.tree.*;

/**
 * This class demonstrates a trivial usage of XTC's Printer class.
 * For much more sophisticated printing, see xtc.lang.CPrinter
 */
public class CppHeadPrinter extends Visitor {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private Printer printer;

    private static int namespaceNum=0;

    private String outputLocation = XtcProps.get("output.location");

    public CppHeadPrinter() {
        Writer w = null;
        try {
            FileOutputStream fos = new FileOutputStream(outputLocation + "/output.h");
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
        namespaceMatch();
        printer.flush(); // important!
    }

    // Print all the node names in an Ast
    public void visit(Node n) {
        //cout(n.getName());//This line is for debugging
        for (Object o : n) if (o instanceof Node) dispatch((Node) o);
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

    private void namespaceMatch() {
        printer.pln();
        while(namespaceNum-- > 0){
            printer.indent().decr().pln("}");
        }
    }

    public void visitCompilationUnit(GNode source){visit(source);}

    public void visitNameSpaceDeclaration(GNode source) {

        String namespace = source.getString(0);
        namespaceNum++;
        printer.indent().p("namespace " + namespace);
        printer.pln(" {");

        //continue to visit sub-nodes
        visit(source);
    }

    public void visitForwardDeclarations(GNode source){
        visit(source);
    }

    public void visitForwardDeclaration(GNode source) {
        String type = source.getString(0);
        String name = source.getString(1);
        printer.pln();
        printer.indent().p(type).pln(" "+name+";");
    }

    public void visitTypeSpecifiers(GNode source){
        visit(source);
    }

    public void visitTypeSpecifier(GNode source){
        String types = source.getString(0);
        String systemType = source.getString(1);
        String CustomType = source.getString(2);
        printer.pln();
        printer.indent().pln(types+" "+systemType+" "+CustomType+";");
    }


    public void visitClassDeclaration(GNode source){
        //get class name and class modifiers
        String classModifiers = source.getString(0);
        String className = source.getString(1);

        //get class body info
        //Node classBody = source.getNode(5);

        //print class dec
        printer.indent().pln("Struct "+className+"{").pln();

        //visit source
        visit(source);

        //close the parentheses
        printer.indent().pln("}");
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
        String name = source.getString(1);
        Node declarators = source.getNode(2);

        for(int i=0;i<modifiers.size();i++){
            Node modifier = modifiers.getNode(i);
            String modifierName = modifier.getString(0);
            printer.indent();
            printer.p(modifierName+" ");
        }

        printer.p(name+" ");

        for(int i=0;i<declarators.size();i++){
            Node declarator = declarators.getNode(i);
            String declaratorName = declarator.getString(0);
            printer.p(declaratorName+" ");
        }

        printer.pln();

        //visit source at the end
        visit(source);
    }

    public void visitConstructorDeclaration(GNode source){

        String className = source.getString(2);
        printer.indent();
        printer.p(className+"(");

        //Formal parameter
        Node formalParameters = source.getNode(3);

        if (formalParameters.getName()=="FormalParameters"){

            for (int i=0;i<formalParameters.size();++i){

                Node formalParameter = formalParameters.getNode(i);
                // printer.indent();
                Node modifier = formalParameter.getNode(0);
                if(modifier.getName()!="null") {
                    printer.p("("+formalParameter.getString(0)+") ");
                }

                printer.p(formalParameter.getString(1)+" "+formalParameter.getString(3));

                if(formalParameters.size()>=1){
                    printer.p(",");
                }
                else
                    printer.p("): ");

            }
        }

        else {
            //there is no formal parameters
            printer.p(")");
        }

        //Initializations

        Node initializations = source.getNode(4);
        if(initializations.getName()=="Initializations"){

            for (int i=0; i<initializations.size();i++){
                Node initialization = initializations.getNode(i);

                printer.p(initialization.getString(0)+"("+initialization.getString(1)+")");

                if (initializations.size()>=1){
                    printer.p(",");
                }

            }
        }

        // last node ?
        if (source.getNode(5).getName() != "null"){
            //to be implemented in later phase
            printer.p("{}");
        }

        else {
            printer.p(";");
        }
    }


    public void visitMethodDeclaration(GNode source){
        //getting method info
        Node modifiers = source.getNode(0);
        String returnType = source.getString(2);
        String methodName = source.getString(3);
        Node formalParameters = source.getNode(4);
        Node block = source.getNode(7);

        //printing modifiers
        if(modifiers.getName() == "Modifiers") {
            for (int i = 0; i < modifiers.size(); i++) {
                Node modifier = modifiers.getNode(i);
                String modifierName = modifier.getString(0);
                printer.indent().p(modifierName + " ");
            }
        }
        //if modifier is none
        else{
            printer.indent();
        }
        //printing returnType and the left parenthesis
        printer.p(returnType+" ");
        printer.p(methodName+"(");

        //printing parameters
        if(formalParameters.getName() == "FormalParameters") {
            for (int i = 0; i < formalParameters.size(); i++) {
                Node formalParameter = formalParameters.getNode(i);
                Node parameterModifiers = formalParameter.getNode(0);

                //if parameterModifiers is not null
                if (parameterModifiers.getName() == "Modifiers") {
                    for (int j = 0; j < parameterModifiers.size(); j++) {
                        String parameterModifierName = parameterModifiers.getString(0);
                        printer.p(parameterModifierName + " ");
                    }
                }

                //do we need to output the type too?
                String parameterType = formalParameter.getString(1);

                //printing parameterName
                String parameterName = formalParameter.getString(3);
                if(i>0){
                    printer.p(", ");
                }
                printer.p(parameterName);
            }
            printer.p(")");
        }

        //else there is no parameter
        else{
            printer.p(" )");
        }

        if(block.getName() == "Block"){
            //print what is inside the block
            //not yet to be implemented in headerfile printing
        }

        else{
            printer.pln(";").pln();
        }

        //visited substructure by hand
        //visit(source);
    }
}

