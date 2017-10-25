package edu.nyu.oop;

import java.util.List;
import scala.collection.immutable.Stream;
import xtc.tree.GNode;

import java.lang.reflect.Field;

public class MethodSignature {

    private List<String> modifier;
    private String return_type;
    private String method_name;
    private List<String> parameters;
    private List<String> parameter_types;

    public MethodSignature(List<String> modifier, String return_type, String method_name, List<String> parameters, List<String> parameter_types) {
        this.modifier = modifier;
        this.return_type = return_type;
        this.method_name = method_name;
        this.parameters = parameters;
        this.parameter_types = parameter_types;
    }

    public List<String> getModifier() {
        return null;
    }

    public String getReturnType(){
        return "";
    }

    public String getMethodName() {
        return "";
    }

    public List<String> getParameters(){
        return null;
    }

    public List<String> getParameterTypes(){
        return null;
    }

    public String setOwner(){
        return "";
    }

    public String getOwner(){
        return "";
    }

}








