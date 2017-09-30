package edu.nyu.oop.util;

import xtc.tree.Node;
import xtc.tree.Visitor;

import org.slf4j.Logger;

import java.util.IdentityHashMap;
import java.util.Map;


// This visits an AST and stores a map of children to their parent nodes.

public class ChildToParentMap {
  private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

  private final Map<Node, Node> map = new IdentityHashMap<Node, Node>();

  public ChildToParentMap(Node n) {
    new Visitor() {
      public void visit(Node n) {
        add(n);
        for (Object o : n) {
          if (o instanceof Node) dispatch((Node) o);
        }
      }
    }.dispatch(n);
  }

  private void add(Node parent) {
    for (int i = 0; i < parent.size(); ++i) {
      Object child = parent.get(i);
      if (child != null && child instanceof Node) {
        map.put((Node) child, parent);
      }
    }
  }

  public Node fetchParentFor(Node child) {
    return map.get(child);
  }

  public Map<Node, Node> getMap() {
    return map;
  }
}