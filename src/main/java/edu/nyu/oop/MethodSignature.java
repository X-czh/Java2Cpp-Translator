package edu.nyu.oop;

import java.util.List;
import xtc.tree.Node;

/**
 * A MethodSignature encapsulates the sufficient info to characterise a method.
 */
public class MethodSignature {

    private List<String> modifier;
    private Node return_type;
    private String method_name;
    private List<String> parameters;
    private List<Node> parameter_types;
    private String owner;
    private Node methodDeclaration;

    public MethodSignature(List<String> modifier, Node return_type, String method_name,
                           List<String> parameters, List<Node> parameter_types,
                           Node methodDeclaration) {
        this.modifier = modifier;
        this.return_type = return_type;
        this.method_name = method_name;
        this.parameters = parameters;
        this.parameter_types = parameter_types;
        this.methodDeclaration = methodDeclaration;
    }

    public List<String> getModifier() {
        return modifier;
    }

    public Node getReturnType(){
        return return_type;
    }

    public void setMethodName(String newName) {
        method_name = newName;
        methodDeclaration.set(3, method_name);
    }

    public String getMethodName() {
        return method_name;
    }

    public List<String> getParameters(){
        return parameters;
    }

    public List<Node> getParameterTypes(){
        return parameter_types;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public String getOwner(){
        return owner;
    }

}








