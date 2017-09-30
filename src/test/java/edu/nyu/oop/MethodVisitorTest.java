package edu.nyu.oop;

import org.junit.*;
import org.slf4j.Logger;
import xtc.tree.GNode;

import static org.junit.Assert.*;

public class MethodVisitorTest {
  private static Logger logger = org.slf4j.LoggerFactory.getLogger(MethodVisitorTest.class);

  private static GNode node = null;

  private MethodVisitor.MethodSummary summary;

  @BeforeClass
  public static void beforeClass() {
    logger.debug("Executing MethodVisitorTest");
    node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test001/Test001.java");
    // XtcTestUtils.prettyPrintAst(node);
  }

  @Before
  public void before() {
    MethodVisitor visitor = new MethodVisitor();
    summary = visitor.getSummary(node);
  }

  @Test
  public void testMethodSummary1() {
    // Assert that the correct number of methods were counted by our visitor
    assertEquals(summary.count, 2);
  }

  @Test
  public void testMethodSummary2() {
    // Assert that the toString method name was collected
    assertTrue(summary.names.contains("toString"));
  }

  @Test
  public void testMethodSummary3() {
    // Assert that the main method name was collected
    assertTrue(summary.names.contains("main"));
  }

}
