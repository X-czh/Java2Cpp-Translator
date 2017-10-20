package edu.nyu.oop;

import edu.nyu.oop.util.*;
import xtc.tree.Node;
import xtc.tree.GNode;
import edu.nyu.oop.CppFilePrinter;

import java.util.*;
/**
 * This is the entry point to the translator facilities. While Boot.java implements the user-program
 * interaction interface--gets command from the command line, and calls the method from the translator,
 * it delegates the work of actually making the ASTs and generating the source files to this class.
 * This class, on the other hand, provides an interface for interaction between Boot.java and the remaining
 * part of the translator. It defines the set of valid methods provided by the translator, and calls them
 * accordingly to fulfill requests made by Boot.java. The delegation pattern is implemented here.
 */

public class Translator {

    public Translator() {}

    private Node root;

    private List<GNode> javaAstList = new ArrayList<GNode>();

    private GNode inheritanceAstRoot;
    private GNode sourceAstRoot;

    public Translator(Node n) {
        root = n;
    }

    public void makeJavaAstList() {
        javaAstList = new JavaFiveImportParser().parse((GNode) root);
    }

    public void makeHeaderAst() {

    }

    public void makeHeaderFile() {
        CppHeadPrinter printer = new CppHeadPrinter();
        printer.print(inheritanceAstRoot,sourceAstRoot);
    }

    public void run() {
        makeJavaAstList();
        makeHeaderAst();
        makeHeaderFile();
    }
}
