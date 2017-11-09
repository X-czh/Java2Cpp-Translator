package edu.nyu.oop;

import java.util.List;
import scala.collection.immutable.Stream;
import xtc.tree.GNode;
import xtc.tree.Node;

import java.lang.reflect.Field;

/**
 * A FieldSignature encapsulates the sufficient info to characterise a field.
 */
public class FieldSignature {
    private List<String> modifier;
    private Node type;
    private List<String> declarators;

    public FieldSignature(List<String> modifier, Node type, List<String> declarators) {
        this.modifier = modifier;
        this.type = type;
        this.declarators = declarators;
    }

    public List<String> getModifier() {
        return modifier;
    }

    public Node getType() {
        return type;
    }

    public List<String> getDeclarators(){
        return declarators;
    }
}