package edu.nyu.oop;

import edu.nyu.oop.util.SymbolTableBuilder;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.type.Type.Tag;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SymbolTableTest {

  private static Logger logger = org.slf4j.LoggerFactory.getLogger(SymbolTableTest.class);

  @BeforeClass
  public static void beforeClass() {
    logger.debug("Executing SymbolTableTest");
  }

  private SymbolTable table;
  private Runtime runtime;

  @Before
  public void before() throws Exception {
    table = new SymbolTable();
    runtime = XtcTestUtils.newRuntime();
  }

  @Test
  public void testSimpleGenerateAndPrint() throws Exception {
    logger.debug("Executing testSimpleGenerateAndPrint");
    GNode node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test006/Test006.java");
    table = new SymbolTableBuilder(runtime).getTable(node);
    new SymbolTablePrinter(runtime, table).full();
  }

  @Test
  public void testSimpleLookup() throws Exception {
    logger.debug("Executing testSimpleLookup");
    Node node = XtcTestUtils.loadTestFile("src/test/java/inputs/test017/Test017.java");
    table = new SymbolTableBuilder(runtime).getTable(node);
    new SymbolTablePrinter(runtime, table).full();

    SymbolTableLookupExample example = new SymbolTableLookupExample(runtime, table);
    Map<String, Tag> summary = example.getSummary(node);
    assertEquals(Tag.INTEGER, summary.get("x"));
    assertEquals(Tag.ARRAY, summary.get("args"));
    assertEquals(Tag.FUNCTION, summary.get("self()"));
    assertEquals(Tag.FUNCTION, summary.get("main()"));
  }

  @Test
  public void testLookup() throws Exception {
    logger.debug("Executing testLookup");
    Node node = XtcTestUtils.loadTestFile("src/test/java/inputs/test020/Test020.java");
    table = new SymbolTableBuilder(runtime).getTable(node);
    new SymbolTablePrinter(runtime, table).full();

    SymbolTableLookupExample example = new SymbolTableLookupExample(runtime, table);
    Map<String, Tag> summary = example.getSummary(node);
    assertEquals(Tag.INTEGER, summary.get("x"));
    assertEquals(Tag.ARRAY, summary.get("args"));
    assertEquals(Tag.FUNCTION, summary.get("x()"));
    assertEquals(Tag.FUNCTION, summary.get("main()"));
    // example.printSummary();
  }

  @Test
  public void testLookupWithShadowedVariables() throws Exception {
    logger.debug("Executing testLookupWithShadowedVariables");
    Node node = XtcTestUtils.loadTestFile("src/test/java/inputs/test006/Test006.java");
    table = new SymbolTableBuilder(runtime).getTable(node);
    //new SymbolTablePrinter(runtime, table).full();
    //runtime.console().format(node).flush();

    SymbolTableLookupExample example = new SymbolTableLookupExample(runtime, table);
    Map<String, Tag> summary = example.getSummary(node);
    assertEquals(Tag.CLASS, summary.get("f"));
    assertEquals(Tag.CLASS, summary.get("a"));
    assertEquals(Tag.ARRAY, summary.get("args"));
    assertEquals(Tag.FUNCTION, summary.get("main()"));
    assertEquals(Tag.FUNCTION, summary.get("A()"));
    assertEquals(Tag.FUNCTION, summary.get("getFld()"));
    assertEquals(Tag.FUNCTION, summary.get("setFld()"));
    assertEquals(Tag.CLASS, summary.get("fld"));
    // example.printSummary();
  }

}