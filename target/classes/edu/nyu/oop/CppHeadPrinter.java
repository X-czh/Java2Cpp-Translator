package edu.nyu.oop;



import java.io.*;
import java.util.*;

import edu.nyu.oop.util.XtcProps;
import org.slf4j.Logger;
import xtc.lang.CPrinter;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Printer;
import xtc.tree.Visitor;
import xtc.tree.Visitor;

import edu.nyu.oop.Boot;

public class CppHeadPrinter extends Visitor {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private Printer printer;


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
      //  this.rootAST = rootAST;
      //  printer.register(this);
    }

    public void print(Node n) {
        headers();

        printer.flush(); // important!
    }

    public void headers(){

        printer.pln("#pragma once");
        printer.pln();
        printer.pln("#include <stdint.h>");
        printer.pln("#include <string>");
        printer.pln("#include \"java_lang.h\"");
        printer.pln("\nusing namespace java::lang;");
        printer.pln();



    }
}