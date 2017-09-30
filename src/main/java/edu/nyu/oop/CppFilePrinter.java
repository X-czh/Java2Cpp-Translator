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

/**
 * This class demonstrates a trivial usage of XTC's Printer class.
 * For much more sophisticated printing, see xtc.lang.CPrinter
 */
public class CppFilePrinter extends Visitor {
  private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

  private Printer printer;

  private String outputLocation = XtcProps.get("output.location");

  public CppFilePrinter() {
    Writer w = null;
    try {
      FileOutputStream fos = new FileOutputStream(outputLocation + "/main.cpp");
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

  public void print(Node n) {
    headOfFile();
    dispatch(n);
    tailOfFile();
    printer.flush(); // important!
  }

  // Print all the node names in an Ast
  public void visit(Node n) {
    cout(n.getName());
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }

  private void headOfFile() {
    printer.pln("#include <iostream>");
    printer.pln("#include \"java_lang.h\"");
    printer.pln();
    printer.pln("using namespace java::lang;");
    printer.pln("using namespace std;");
    printer.pln();
    printer.pln("int main(void) {");
  }

  private void cout(String line) {
    printer.incr().indent().pln("cout << \"" + line + "\" << endl;").decr();
  }

  private void tailOfFile() {
    printer.incr().indent().pln("return 0;");
    printer.decr(); // not really necessary, but for demonstration.
    printer.pln("}");
  }
}