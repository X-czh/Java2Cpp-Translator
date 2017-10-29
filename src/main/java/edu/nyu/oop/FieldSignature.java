package edu.nyu.oop;

import java.util.List;
import scala.collection.immutable.Stream;
import xtc.tree.GNode;

import java.lang.reflect.Field;

public class FieldSignature {
    private List<String> modifier;
    private String type;
    private List<String> declarators;

    public FieldSignature(List<String> modifier, String type, List<String> declarators) {
        System.out.println("fuck2" + declarators.get(0));
        this.modifier = modifier;
        this.type = type;
        this.declarators = declarators;
    }

    public List<String> getModifier() {
        return modifier;
    }

    public String getType() {
        return type;
    }

    public List<String> getDeclarators(){
        return declarators;
    }
}