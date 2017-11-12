package edu.nyu.oop.util;

import org.slf4j.Logger;
import xtc.parser.ParseException;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Visitor;

import java.io.*;
import java.util.*;

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

  private Set<GNode> sourceTable;  // keep track of the sources parsed or imported by import parser
  private Set<GNode> primarySourceTable;  // keep track of the sources parsed as a primary source by import parser

  public JavaFiveImportParser() {
    sourceTable = new HashSet<GNode>();
    primarySourceTable = new HashSet<GNode>();
  }

  public List<GNode> parse(final GNode primarySrc) {
    final List<GNode> importedSources = new LinkedList<GNode>();

    sourceTable.add(primarySrc);
    primarySourceTable.add(primarySrc);

    new Visitor() {

      public void visitPackageDeclaration(GNode node) throws IOException, ParseException {
        String relPath = NodeUtil.mkString(node.getNode(1), File.separator);
        importedSources.addAll(loadNodesFromDirectory(relPath));
      }

      public void visitImportDeclaration(GNode node) {
        if (node.getString(2) == null) {   // There is no '*' character in the import, import single file.
          String relPath = NodeUtil.mkString(node.getNode(1), File.separator) + ".java";
          GNode n = loadNodeForFile(relPath);
          if (n != null) {
            importedSources.add(n);
            sourceTable.add(n);
          }
        } else {
          String relPath = NodeUtil.mkString(node.getNode(1), File.separator);
          List<GNode> sources = loadNodesFromDirectory(relPath);
          if (sources != null) {
            importedSources.addAll(sources);
            sourceTable.addAll(sources);
          }
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
            GNode n = (GNode) NodeUtil.parseJavaFile(f);
            if (!sourceTable.contains(n)) source = n;
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
              if (!sourceTable.contains(n)) sources.add(n);
            }
            break; // stop at the first input location containing the package of the primary source
          }
        }
        return sources;
      }

    }.dispatch(primarySrc);

    // recursively parse dependencies
    List<GNode> tmpImportedSources = new LinkedList<GNode>(importedSources);
    for (GNode s : tmpImportedSources) {
      if (!primarySourceTable.contains(s)) // skip if already parsed by import parser
        importedSources.addAll(parse(s));
    }

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