package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import org.junit.Test;
import xtc.lang.JavaEntities;
import xtc.tree.GNode;

import static org.junit.Assert.assertEquals;

public class StringToNodeExample {

  final String file = "" +
          "package a.b;" +
          "class A {                  " +
          "  public int i1;           " +
          "  public void m(char c) {  " +
          "    int[] a1;              " +
          "    final int[] a2;        " +
          "  }                        " +
          "}                          ";

  @Test
  public void test() throws Exception {
    GNode node = JavaEntities.javaStringToAst("CompilationUnit", file, true);
    GNode m = (GNode) NodeUtil.dfs(node, "MethodDeclaration");
    assertEquals("m", m.get(3));
  }
}
