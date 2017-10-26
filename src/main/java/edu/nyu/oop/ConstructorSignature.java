package edu.nyu.oop;

import java.util.List;
import scala.collection.immutable.Stream;
import xtc.tree.GNode;

import java.lang.reflect.Field;

public class ConstructorSignature {
    private String name;
    private List<String> parameters;
    private List<String> parameter_types;

    public ConstructorSignature(String name, List<String> parameters, List<String> parameter_types){
        this.name = name;
        this.parameters = parameters;
        this.parameter_types = parameter_types;
    }

    public String getName(){
        return name;
    }

    public List<String> getParameters(){
        return parameters;
    }

    public List<String> getParameterTypes(){
        return parameter_types;
    }
}