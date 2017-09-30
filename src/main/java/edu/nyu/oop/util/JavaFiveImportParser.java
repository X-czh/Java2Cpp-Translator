package edu.nyu.oop.util;

import org.slf4j.Logger;
import xtc.parser.ParseException;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Visitor;

import java.io.*;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

/**
 * This is a utility class which will load the source files for anything referenced by the primary source.
 * Note that it does *not* do this recursively. In other words, it will not return nodes representing the
 * dependencies of the dependencies. You can obviously do that yourself using this class if necessary.
 * <p>
 * It will look for the files in the locations specified in xtc.properties's input.locations property.
 * If you have two packages named the same thing under more than one input location, it will return
 * which ever one it finds first.
 */
public class JavaFiveImportParser {
  private static Logger logger = org.slf4j.LoggerFactory.getLogger(JavaFiveImportParser.class);

  private static List<String> inputLocations = loadInputLocations();

  public static List<GNode> parse(final GNode primarySrc) {
    final List<GNode> importedSources = new LinkedList<GNode>();

    new Visitor() {

      public void visitPackageDeclaration(GNode node) throws IOException, ParseException {
        String relPath = NodeUtil.mkString(node.getNode(1), File.separator);
        importedSources.addAll(loadNodesFromDirectory(relPath));
      }

      public void visitImportDeclaration(GNode node) {
        if (node.getString(2) == null) {   // There is no '*' character in the import, import single file.
          String relPath = NodeUtil.mkString(node.getNode(1), File.separator) + ".java";
          importedSources.add(loadNodeForFile(relPath));
        } else {
          String relPath = NodeUtil.mkString(node.getNode(1), File.separator);
          importedSources.addAll(loadNodesFromDirectory(relPath));
        }
      }

      public void visit(Node n) {
        for (Object o : n) if (o instanceof Node) dispatch((Node) o);
      }

      private GNode loadNodeForFile(String relPath) {
        GNode source = null;
        for (String l : inputLocations) {
          String absPath = System.getProperty("user.dir") + File.separator + l + File.separator + relPath;
          File f = loadSourceFile(absPath);
          if (f != null) {
            source = (GNode) NodeUtil.parseJavaFile(f);
            break;
          }
        }
        if (source == null) {
          logger.warn("Unable to find any source file for path " + relPath);
        }

        return source;
      }

      private List<GNode> loadNodesFromDirectory(String relPath) {
        List<GNode> sources = new LinkedList<GNode>();
        for (String l : inputLocations) {
          String absPath = System.getProperty("user.dir") + File.separator + l + File.separator + relPath;
          Set<File> files = loadFilesInDirectory(absPath);
          if (files != null) {
            for (File f : files) {
              GNode n = (GNode) NodeUtil.parseJavaFile(f);
              if (!n.equals(primarySrc)) sources.add(n); // Don't include the primary source.
            }
            break; // stop at the first input location containing the package of the primary source
          }
        }
        return sources;
      }

    }.dispatch(primarySrc);

    return importedSources;
  }

  private static Set<File> loadFilesInDirectory(String path) {
    File[] directory = new File(path).listFiles();
    if (directory == null) {
      logger.debug("Did not find a directory at " + path);
      return null;
    }

    Set<File> files = new HashSet<File>();
    for (File f : new File(path).listFiles()) {
      File source = loadSourceFile(f.getAbsolutePath());
      if (source != null) files.add(source);
    }
    if (files.size() == 0) {
      logger.warn("Path with no source files. " + path);
      return null;
    }

    return files;
  }

  private static File loadSourceFile(String path) {
    File f = new File(path);
    if (f == null) {
      logger.warn("Invalid path provided for file. " + path);
      return null;
    }

    if (f.isFile() && f.getName().endsWith(".java")) {
      logger.debug("Loading " + f.getName());
      return f;
    } else {
      return null;
    }
  }

  private static List<String> loadInputLocations() {
    // Load input locations with correct file separator for system
    List<String> inputLocations = new LinkedList<String>();
    for (String l : XtcProps.getList("input.locations")) {
      inputLocations.add(l.replaceAll("/", File.separator));
    }
    return inputLocations;
  }
}