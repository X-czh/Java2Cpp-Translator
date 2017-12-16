package edu.nyu.oop;

import edu.nyu.oop.util.*;

import xtc.tree.Node;
import xtc.tree.GNode;

import java.util.*;

import xtc.util.Runtime;
import xtc.util.SymbolTable;

/**
 * This is the entry point to the translator facilities. While Boot.java implements the user-program
 * interaction interface--gets command from the command line, and calls the method from the translator,
 * it delegates the work of actually making the ASTs and generating the source files to this class.
 * This class, on the other hand, provides an interface for interaction between Boot.java and the remaining
 * part of the translator. It defines the set of valid methods provided by the translator, and calls them
 * accordingly to fulfill requests made by Boot.java. The delegation pattern is implemented here.
 */
public class Translator {
    private Runtime runtime;
    private Node root;
    private Map<String, ClassSignature> classTreeMap;
    private List<String> packageInfo;
    private List<String> conflictMethodNames;
    private List<Node> javaAstList;
    private Node headerAst;
    private Node mutatedCppAst;
    private Node mainAst;

    public Translator(Runtime rt, Node n) {
        runtime = rt;
        root = n;
    }

    private void makeJavaAstList() {
        javaAstList = new ArrayList<>();
        javaAstList.add(root);
        javaAstList.addAll(new JavaFiveImportParser().parse((GNode) root));
    }

    private void mutateJavaAstList() {
        SymbolTable table = new SymbolTable();
        for (Node n : javaAstList)
            table = new SymbolTableBuilder(runtime, table).getTable(n);
        ContextualMutator contextualMutator = new ContextualMutator(runtime, table);
        contextualMutator.mutate(javaAstList);
    }

    private void makeHeaderAst() {
        ClassTreeVisitor classTreeVisitor = new ClassTreeVisitor();
        classTreeMap = classTreeVisitor.getClassTree(javaAstList);
        packageInfo = classTreeVisitor.getPackageInfo();
        conflictMethodNames = classTreeVisitor.getConflictMethodNames();
        HeaderAstBuilder headerAstBuilder = new HeaderAstBuilder(classTreeMap, packageInfo);
        headerAst = headerAstBuilder.buildHeaderAst();
    }

    private void makeHeaderFile() {
        CppPrinter cppPrinter = new CppPrinter("/output.h");
        cppPrinter.printHeader(headerAst);
    }

    private void makeMutatedCppAst() {
        Mutator mutator = new Mutator(classTreeMap, packageInfo, conflictMethodNames);
        mutatedCppAst = mutator.mutate(javaAstList);
        mainAst = mutator.makeMainAst();
    }

    private void makeImplementationFiles() {
        CppPrinter cppOutputPrinter = new CppPrinter("/output.cpp");
        CppPrinter cppMainPrinter = new CppPrinter("/main.cpp");
        cppOutputPrinter.printCpp(mutatedCppAst);
        cppMainPrinter.printMain(mainAst);
    }

    public void run() {
        makeJavaAstList();
        mutateJavaAstList();
        makeHeaderAst();
        makeHeaderFile();
        makeMutatedCppAst();
        makeImplementationFiles();
    }

    public List<Node> getJavaAstList() {
        makeJavaAstList();
        return javaAstList;
    }

    public List<Node> getMutatedJavaAstList() {
        makeJavaAstList();
        mutateJavaAstList();
        return javaAstList;
    }

    public Node getHeaderAst() {
        makeJavaAstList();
        mutateJavaAstList();
        makeHeaderAst();
        return headerAst;
    }

    public void printCppHeader() {
        makeJavaAstList();
        mutateJavaAstList();
        makeHeaderAst();
        makeHeaderFile();
    }

    public Node getMutatedCppAst() {
        makeJavaAstList();
        mutateJavaAstList();
        makeHeaderAst();
        makeMutatedCppAst();
        return mutatedCppAst;
    }

    public Node getMainAst() {
        makeJavaAstList();
        mutateJavaAstList();
        makeHeaderAst();
        makeMutatedCppAst();
        return mainAst;
    }

    public void printCppImplementation() {
        makeJavaAstList();
        mutateJavaAstList();
        makeHeaderAst();
        makeMutatedCppAst();
        makeImplementationFiles();
    }

}
