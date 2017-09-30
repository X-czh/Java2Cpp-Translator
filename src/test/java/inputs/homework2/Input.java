package inputs.homework2;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import xtc.lang.JavaFiveParser;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * Step 1:
 * Identify all language constructs in this source file. Moreover all the components
 * from our restricted version of Java specified on the course home page, which introduce a new
 * programming language scope.  Your list should start with classes:
 *
 *   The body of class declarations
 *   ...
 *
 */
public class Input extends xtc.util.Tool {

    public Input() {
        // Nothing to do.
    }

    {
        // A class-level anonymous scope.
    }

    public interface ICommand {
        // This is a scope too!
        public void run();
    }

    public String getName() {
        return "Homework2Input";
    }

    public File locate(String name) throws IOException {
        File file = super.locate(name);
        if (Integer.MAX_VALUE < file.length()) {
            throw new IllegalArgumentException(file + ": file too large");
        }
        return file;
    }

    public Node parse(Reader in, File file) throws IOException, ParseException {
        JavaFiveParser parser =
            new JavaFiveParser(in, file.toString(), (int)file.length());
        Result result = parser.pCompilationUnit(0);
        return (Node)parser.value(result);
    }

    public void process(Node node) {
        new Visitor() {
            public void visit(Node n) {
                for (Object o : n) {
                    // The scope belongs to the for loop!
                    if (o instanceof Node) dispatch((Node) o);
                }
            }

        } .dispatch(node);
    }

    public static void main(String[] args) {
        new Input().run(args);
    }

}
