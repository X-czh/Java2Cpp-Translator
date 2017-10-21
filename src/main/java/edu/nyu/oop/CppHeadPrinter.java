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

    public void print(GNode inheritance, GNode source) {
        headOfFile();
        visit(source);
        tailOfFile();
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

    private void tailOfFile() {
        printer.pln();
        while(namespaceNum-- > 0){
            printer.indent().decr().pln("}");
        }
    }

    public void visitCompilationUnit(GNode source){visit(source);}

    public void visitPackageDeclaration(GNode source) {

        Node qualifiedIdentifier = source.getNode(1);

        for (int i = 0; i < qualifiedIdentifier.size(); i++) {
            namespaceNum++;
            printer.indent().incr().p("namespace " + qualifiedIdentifier.get(i).toString());
            printer.pln(" {");

        }

        visit(source);
    }

    public void visitClassDeclaration(GNode source){

        //get class name and class modifiers
        Node classModifiers = source.getNode(0);
        String className = source.getString(1);

        //get class methods info
        Node classBody = source.getNode(5);
        Node classMethod = classBody.getNode(0);
        Node classMethodModifiers = classMethod.getNode(0);//need for loop

        //this is unsure how to get VoidType()
        Object methodReturnType = classMethod.get(2);
        String methodName = classMethod.getString(3);

        //visit source at the end
        visit(source);
    }
}

