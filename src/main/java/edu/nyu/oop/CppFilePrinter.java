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
  public void visitConstructorDeclaration(GNode source){

   String className = source.getString(2);
   printer.indent();
   printer.p(className+"(");
   
   //Formal parameter 
   Node formalParameter = source.getNode(3);

   if (formalParameter.getName()=="FormalParameters"){

    for (int i=0;i<formalParameter.size();++i){

    // printer.indent();
     Node modifier = formalParameter.getNode(0);
     if(modifier.getName()!="null") {
      printer.p("("+formalParameter.getString(0)+") ");
     }
     
     printer.p(formalParameter.getString(1)+" "+formalParameter.getString(3));

     if(formalParameter.size()>=1){
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

    Node initialization = source.getNode(4);
    if(initialization.getName()=="Initializations"){

      for (int i=0; i<initialization.size();i++){
        printer.p(initialization.getString(0)+"("+initialization.getString(1)+")");

        if (initialization.size()>=1){
          printer.p(",");
        }

      }
    }

    // last node ?
    if (formalParameter.getNode(formalParameter.size()-1).getName() != "null"){
      printer.p("{}");
    }

    else {

      printer.p(";");
    }

   }

   



}