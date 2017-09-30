package edu.nyu.oop.util;

import xtc.tree.Node;
import xtc.tree.Visitor;

public abstract class RecursiveVisitor extends Visitor {
    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }
}