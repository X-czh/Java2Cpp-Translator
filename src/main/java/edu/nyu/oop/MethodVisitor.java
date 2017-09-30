package edu.nyu.oop;

import org.slf4j.Logger;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.List;
import java.util.ArrayList;

/**
 * This class demostrates a trivial usage of Xtc's Visitor class.
 * You may use this as a base for your ScopeVisitor.
 */
public class MethodVisitor extends Visitor {
  private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

  private MethodSummary summary = new MethodSummary();

  public void visitCompilationUnit(GNode n) {
    visit(n);
  }

  public void visitMethodDeclaration(GNode n) {
    summary.nodes += n.getName() + " ";
    summary.names += n.getString(3) + " ";
    summary.count++;
    visit(n);
  }

  public void visit(Node n) {
    for (Object o : n) if (o instanceof Node) dispatch((Node) o);
  }

  public MethodSummary getSummary(Node n) {
    super.dispatch(n);
    return summary;
  }

  // An instance of this class will be mutated as the AST is traversed.
  static class MethodSummary {
    int count = 0;
    String names = "";
    String nodes = "";

    public String toString() {
      return "Method count: " + count + System.lineSeparator() +
              "Method names: " + names + System.lineSeparator() +
              "Node names: " + nodes + System.lineSeparator();
    }
  }
}