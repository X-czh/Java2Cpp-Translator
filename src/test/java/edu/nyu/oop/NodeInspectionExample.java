package edu.nyu.oop;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import xtc.tree.GNode;

public class NodeInspectionExample {

  private static Logger logger = org.slf4j.LoggerFactory.getLogger(NodeInspectionExample.class);

  private static GNode node;

  @BeforeClass
  public static void beforeClass() {
    logger.debug("Executing NodeInspectionExample");
    node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/homework2/Input.java");
    // XtcTestUtils.prettyPrintAst(node);
  }

  @Test
  public void example() {
    // Every GNode that *is not null* has a name.
    logger.debug("Root node name is " + node.getName());

    // Every GNode can have children which are other nodes.
    // You can loop through them and see what they are.
    GNode pkg = GNode.create("");
    for (int i = 0; i < node.size(); ++i) {
      GNode child = node.getGeneric(i);
      logger.debug("Child " + i + "'s name is " + child.getName());
      if (child.getName().equals("PackageDeclaration")) {
        pkg = child;
      }
    }

    // You can examine each node in the tree to find out what it contains.
    logger.debug("PackageDeclaration node looks like this " + pkg.toString());

    // And you may need to reach into a particular child to get what ou want.
    GNode qualifiedId = (GNode) pkg.get(1);
    for (int i = 0; i < qualifiedId.size(); ++i) {
      Object child = qualifiedId.get(i);
      // Some GNodes have children that are not GNodes, for example they could be null or a string.
      if (child != null) {
        logger.debug("Type of child is  " + child.getClass());
      }
    }
  }


}



