package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import xtc.tree.GNode;

import static org.junit.Assert.*;

public class NodeUtilTest {

  private static Logger logger = org.slf4j.LoggerFactory.getLogger(NodeUtilTest.class);

  private static GNode node;

  @BeforeClass
  public static void beforeClass() {
    logger.debug("Executing NodeUtilTest");
    node = (GNode) XtcTestUtils.loadTestFile("src/test/java/edu/nyu/oop/XtcTestUtils.java");
  }

  @Test
  public void testMkString() {
    // In the case where all the children are strings, build a concatenation of the values.
    GNode pkg = (GNode) NodeUtil.dfs(node, "PackageDeclaration");
    GNode id = (GNode) NodeUtil.dfs(pkg, "QualifiedIdentifier");
    String packageName = NodeUtil.mkString(id, ".");
    assertEquals("edu.nyu.oop", packageName);

    // In the case where all the children are not strings, build a concatenation of the toStrings.
    GNode mods = (GNode) NodeUtil.dfs(node, "Modifiers");
    String modifier = NodeUtil.mkString(mods, "");
    assertEquals("Modifier(\"public\")", modifier);
  }

  @Test
  public void testDeepCopy() {
    // we can use the GNode given by the node variable
    GNode duplicatedNode = NodeUtil.deepCopyNode(node);
    testDuplicate(node, duplicatedNode);
  }

  private void testDuplicate(GNode a, GNode b) {
    assertEquals("GNodes hold the same data", a, b);
    assertNotSame("GNodes are different locations in memory", a, b);
    if (a != null) {
      for (int i = 0; i < a.size(); i++) {
        Object child = a.get(i);
        if (child instanceof GNode) {
          GNode aChild = GNode.cast(child);
          GNode bChild = GNode.cast(b.get(i));
          testDuplicate(aChild, bChild);
        }
      }
    }
  }
}
