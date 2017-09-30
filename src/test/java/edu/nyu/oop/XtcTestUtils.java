package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import xtc.lang.JavaEntities;
import xtc.tree.Node;
import xtc.util.Runtime;

import java.io.File;

public class XtcTestUtils {

  public static Runtime newRuntime() {
    Runtime runtime = new Runtime();
    runtime.initDefaultValues();
    runtime.dir("in", Runtime.INPUT_DIRECTORY, true, "");
    runtime.setValue(Runtime.INPUT_DIRECTORY, JavaEntities.TEMP_DIR);
    return runtime;
  }

  public static Node loadTestFile(String filename) {
    File file = new File(filename);
    return NodeUtil.parseJavaFile(file);
  }

  public static void prettyPrintAst(Node node) {
    newRuntime().console().format(node).pln().flush();
  }

}
