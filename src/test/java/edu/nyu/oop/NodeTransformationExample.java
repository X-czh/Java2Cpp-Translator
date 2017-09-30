package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import xtc.tree.GNode;
import xtc.tree.Node;

import java.util.List;

public class NodeTransformationExample {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(NodeTransformationExample.class);

    @BeforeClass
    public static void beforeClass() {
        logger.debug("Executing NodeTransformationExample");
    }

    // You can replace nodes in an Ast with copies that can have variable number of children.
    // In this way you mutate the Ast to contain new, arbitrarily nested nodes types.
    // This is one way to "decorate" your tree to help in translation.
    @Test
    public void addNewNodes() {
        logger.debug("Begin addNewNodes");

        GNode root = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test003/Test003.java");
        GNode method = (GNode) NodeUtil.dfs(root, "MethodDeclaration");

        logger.debug("Before...");
        XtcTestUtils.prettyPrintAst(method);

        // Pull out a node we want to mutate
        GNode modifiers = (GNode) method.get(0);

        // If it is a fixed size GNode, convert it to a variable size one and replace it in the ast
        if(!modifiers.hasVariable()) {
            modifiers = GNode.ensureVariable(modifiers);
            method.set(0, modifiers);
        }

        // Create some new nodes...
        GNode parent = GNode.create("SomeNewNodeType");
        parent.add(0, GNode.create("ChildOne", new Integer(method.size())));
        parent.add(1, GNode.create("ChildTwo", "I am child two!"));

        // Add the new nodes to the parent..
        modifiers.add(parent);

        logger.debug("After...");
        XtcTestUtils.prettyPrintAst(method);
    }

    // For the purposes of v1 of the translator we just want to get it working
    // for as many inputs as possible, so start with the simplest and do it ad-hoc.
    // i.e. just getting working for the simplest input and then as you move through
    // the inputs start generalizing and abstracting your mutation methods.
    // Here is a trivial example:
    //   Change the right hand side of new expressions to match the C++ type.
    //   Ex.
    //     B b = new B();
    //   Becomes..
    //     B b = new __B();
    // Note that a lot of the code here is just boilerplate stuff that I need to get at the thing I want to change.
    // Must of this would not be necessary if I was using visitors.
    @Test
    public void adHocNodeMutation() {
        logger.debug("Begin mutateNodes");

        // Find main method
        GNode root = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test001/Test001.java");
        List<Node> methods = NodeUtil.dfsAll(root, "MethodDeclaration");
        GNode main = null;
        for(Node n : methods) {
            if(n.get(3).equals("main")) {
                main = (GNode) n;
            }
        }

        // Find the any declarations inside the main method
        Node declarator = NodeUtil.dfs(NodeUtil.dfs(main, "Block"), "Declarator");

        logger.debug("Before...");
        XtcTestUtils.prettyPrintAst(declarator);

        // Change the right hand side of any declaration to the right C++ type name
        for(int i = 0 ; i < declarator.size() ; ++i) {
            try {
                GNode child = declarator.getGeneric(i);
                if(child.hasName("NewClassExpression")) {
                    GNode id = (GNode) NodeUtil.dfs(child, "QualifiedIdentifier");
                    id.set(0, "__" + id.get(0));
                }
            } catch(Exception e) {
                // its not a generic node, so we are not
                // interested in it for the purposes of this example.
            }
        }

        logger.debug("After...");
        XtcTestUtils.prettyPrintAst(declarator);
    }

}