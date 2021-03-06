package edu.nyu.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import edu.nyu.oop.util.NodeUtil;
import xtc.lang.JavaFiveParser;
import xtc.lang.JavaPrinter;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import xtc.util.Tool;

/**
 * An example of a simple use of XTC's basic capabilities.
 * <p>
 * In our project translators we will not have so many
 * different things going on in one class, (e.g. there will be different
 * classes for our 'Tool' implmenetations and our 'Visitor' implementations)
 * <p>
 * This is run by opening sbt and using the command..
 * 'run -countMethods ./src/test/java/inputs/test000/Test000.java'
 * <p>
 * You will have to select which main method to run from a menu.
 * The filename argument can be changed to any java file under the project root.
 */
public class SimpleXtc extends Tool {

  // getName, getCopy, init, prepare, locate, parse, and process methods are
  // all overriden from the Tool super-class.

  // A Tool in XTC is a set of user interface-related functionalities
  // such as declaring valid command-line arguments and source files location.

  public String getName() {
    return "Method Counter";
  }

  public String getCopy() {
    return "In-class demo of translator.";
  }

  public void init() {
    super.init();
    // Declare command line arguments.
    runtime.
    bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
    bool("countMethods", "countMethods", false, "Count all Java methods.");
  }

  public void prepare() {
    super.prepare();
    // Perform consistency checks on command line arguments.
  }

  // The locate method locates the file name that was passed in from the command line, and
  // checks that its length is not larger than the max value of a Java Integer and that its
  // its in a path located under the project root (whereever you checked out this repository)
  public File locate(String name) throws IOException {
    File file = super.locate(name);
    if (Integer.MAX_VALUE < file.length()) {
      throw new IllegalArgumentException("File too large " + file.getName());
    }
    if (!file.getAbsolutePath().startsWith(System.getProperty("user.dir"))) {
      throw new IllegalArgumentException("File must be under project root.");
    }
    return file;
  }

  // The parse method parses the content of the file and then returns the AST that was generated.
  @Override
  public Node parse(Reader in, File file) throws IOException, ParseException {
    return NodeUtil.parseJavaFile(file);
  }

  // The process method processes the AST that was generated by the parse method,
  // and then depending on what command line arguments were passed, will print the
  // Java AST or visit the nodes and count methods in this example.
  public void process(Node node) {

    // When present, the "-printJavaAST" argument calls XTC's runtime.console() method
    // and formats the AST, prints a new line, and then flushes the output buffer.
    // Flushing the output buffer is important, if you do not, you might not see the output you expect.
    if (runtime.test("printJavaAST")) {
      runtime.console().format(node).pln().flush();
    }

    if (runtime.test("countMethods")) {
      // The Visitor class is the key thing to notice. It allows you to traverse the Java AST.
      new Visitor() {
        // Notice that the Visitor class inside the process method is an example of an anonymous class.
        // http://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html

        private int count = 0;

        // The names of these methods are in fact very important...
        // any method named visitXXX will be called on any AST node of type XXX.
        // There are some subtle things going on related to XTC even in this small class.
        public void visitCompilationUnit(GNode n) {
          visit(n);
          runtime.console().p("Number of methods: ").p(count).pln().flush();
        }

        public void visitMethodDeclaration(GNode n) {
          runtime.console().p("Name of node: ").p(n.getName()).pln();
          runtime.console().p("Name of method: ").p(n.getString(3)).pln();
          visit(n);
          count++;
        }

        // The method call visit(n) will recursively visit the children of node n.
        // Each time visit is called, we use for loop to iterate over all the children of the
        // node and for each child which is a node call dispatch which dispatches this visitor on that node.
        public void visit(Node n) {
          for (Object o : n) if (o instanceof Node) dispatch((Node) o);
        }

      } .dispatch(node);
    }
  }

  /**
   * Run with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    new SimpleXtc().run(args);
  }
}