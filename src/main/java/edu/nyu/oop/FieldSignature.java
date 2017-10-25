package edu.nyu.oop;

import java.util.List;
import scala.collection.immutable.Stream;
import xtc.tree.GNode;

import java.lang.reflect.Field;

public class FieldSignature {
    private List<String> modifier;
    private String name;
    private String type;

    public FieldSignature(List<String> modifier, String name, String type) {
        this.modifier = modifier;
        this.name = name;
        this.type = type;
    }

    public List<String> getModifier() {
        return null;
    }

    public String getName(){
        return "";
    }

    public String getType() {
        return "";
    }

}