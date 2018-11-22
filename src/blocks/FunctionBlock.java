package blocks;

import core.*;
import types.PrimitiveType;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class FunctionBlock extends Block<Value> {

    public boolean isClosed = false;
    private String functionName;
    private String returnTypeIdentifier;
    private Type returnType;
    private ArrayList<String[]> parameters;
    private int parametersCount;
    private AccessModifier accessModifier;
    private Value returnValue;

    public FunctionBlock(Block parentBlock, String line, int lineNumber, String name, AccessModifier accessModifier, String returnType, ArrayList<String[]> parameters) {
        super(parentBlock, line, lineNumber);

        this.functionName = name;

        try {
            this.accessModifier = accessModifier;
        } catch (Exception exception) {
            throw getIllegalStateException("Invalid access modifier " + accessModifier);
        }

        this.returnTypeIdentifier = returnType;

        this.parameters = parameters;

        this.parametersCount = parameters.size();
    }

    @Override
    public Value run() {
        return this.invoke();
    }

    public Value invoke(Value... values) {
        ArrayList<Parameter> realParameters = new ArrayList<>();

        for (String[] parametersData : parameters) {
            realParameters.add(new Parameter(Type.match(parametersData[0], this), parametersData[1]));
        }

        if (values.length != realParameters.size()) {
            throw getIllegalStateException("Invalid number of arguments passed");
        }

        returnType = Type.match(returnTypeIdentifier, this);

        for (int parameterIndex = 0, maxIndex = values.length; parameterIndex < maxIndex; parameterIndex++ ) {
            Parameter parameter = realParameters.get(parameterIndex);

            Value value = values[parameterIndex];

            if (parameter.getType() != value.getType() && false == value.getType().toString().toLowerCase().equals("null")) {
                throw getIllegalStateException(
                        "Invalid value type. Required " +
                                parameter.getType().toString() + " got " + value.getType().toString()
                );
            }

            setVariable(new Variable(this, parameter.getName(), parameter.getType(), value.getValue()));
        }


        for (Block block : getChildren()) {
            block.run();

            if (null != returnValue) {
                break;
            }
        }

        if (null == returnValue && PrimitiveType.VOID != returnType) {
            throw getIllegalStateException("Function " + functionName + " must return " + returnType.toString());
        }

        Value localReturnValue = returnValue;

        returnValue = null;

        return localReturnValue;
    }

    public void setReturnValue(Value value) {
        if (false == value.getType().equals(returnType)) {
            throw getIllegalStateException("Invalid return type, expected " + returnType + " got " + value.getType());
        }

        returnValue = value;
    }

    public int getParametersCount() {
        return parametersCount;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getReturnTypeIdentifier() {
        return returnTypeIdentifier;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public ArrayList<String[]> getParameters() {
        return parameters;
    }
}
