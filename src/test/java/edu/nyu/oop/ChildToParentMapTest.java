package edu.nyu.oop;

import edu.nyu.oop.util.ChildToParentMap;
import edu.nyu.oop.util.NodeUtil;

import xtc.lang.JavaEntities;
import xtc.tree.GNode;
import xtc.tree.Node;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChildToParentMapTest {

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
    ChildToParentMap map = new ChildToParentMap(node);

    Node declaration = NodeUtil.dfs(node, "ClassDeclaration");
    assertEquals("CompilationUnit", map.fetchParentFor(declaration).getName());

    Node pakage = NodeUtil.dfs(node, "PackageDeclaration");
    assertEquals("CompilationUnit", map.fetchParentFor(pakage).getName());

    Node body = NodeUtil.dfs(node, "ClassBody");
    assertEquals("ClassDeclaration", map.fetchParentFor(body).getName());

    Node params = NodeUtil.dfs(node, "FormalParameters");
    assertEquals("MethodDeclaration", map.fetchParentFor(params).getName());
  }
}
