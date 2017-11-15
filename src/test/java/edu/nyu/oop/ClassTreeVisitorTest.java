package edu.nyu.oop;
import org.junit.*;
import org.slf4j.Logger;
import xtc.tree.Node;
import xtc.tree.GNode;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClassTreeVisitorTest {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(MethodVisitorTest.class);

    private ClassSignature current_class;

    @BeforeClass
    public static void beforeClass(){
        logger.debug("Executing ClassTreeVisitorTest");
    }

    @Test
    public void test001(){
        ClassTreeVisitor visitor = new ClassTreeVisitor();
        List<Node> input_ast = new ArrayList<Node>();
        GNode node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test001/Test001.java");
        input_ast.add(node);
        Map<String,ClassSignature> tree_map = visitor.getClassTree(input_ast);

        current_class = tree_map.get("A");
        List<MethodSignature> methods = current_class.getMethodList();
        ArrayList<String> allMethods = new ArrayList<String>();
        for(MethodSignature method : methods){
            String method_name = method.getMethodName();
            allMethods.add(method_name);
        }
        assertTrue(allMethods.contains("toString"));
    }

    public void test003(){
        ClassTreeVisitor visitor = new ClassTreeVisitor();
        List<Node> input_ast = new ArrayList<Node>();
        GNode node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test003/Test003.java");
        input_ast.add(node);
        Map<String,ClassSignature> tree_map = visitor.getClassTree(input_ast);

        current_class = tree_map.get("A");
        List<ConstructorSignature> constructors = current_class.getConstructorList();
        ArrayList<String> allConstructors = new ArrayList<String>();
        for(ConstructorSignature constructor : constructors){
            String constructor_name = constructor.getName();
            allConstructors.add(constructor_name);
        }
        assertTrue(allConstructors.contains("A"));

        List<MethodSignature> methods = current_class.getMethodList();
        ArrayList<String> allMethods = new ArrayList<String>();
        for(MethodSignature method : methods){
            String method_name = method.getMethodName();
            allMethods.add(method_name);
        }
        assertTrue(allMethods.contains("getFld"));

    }

    public void test012(){
        ClassTreeVisitor visitor = new ClassTreeVisitor();
        List<Node> input_ast = new ArrayList<Node>();
        GNode node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test012/Test012.java");
        input_ast.add(node);
        Map<String,ClassSignature> tree_map = visitor.getClassTree(input_ast);

        current_class = tree_map.get("C");
        List<ConstructorSignature> constructors = current_class.getConstructorList();
        ArrayList<String> allConstructors = new ArrayList<String>();
        for(ConstructorSignature constructor : constructors){
            String constructor_name = constructor.getName();
            allConstructors.add(constructor_name);
        }
        assertTrue(allConstructors.contains("c"));

        List<MethodSignature> methods = current_class.getMethodList();
        ArrayList<String> allMethods = new ArrayList<String>();
        for(MethodSignature method : methods){
            String method_name = method.getMethodName();
            allMethods.add(method_name);
        }
        assertTrue(allMethods.contains("myToString"));
    }

}
