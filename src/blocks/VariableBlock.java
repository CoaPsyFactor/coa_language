package blocks;

import core.Block;
import core.Type;
import core.Value;
import core.Variable;
import tokenizer.Token;
import tokenizer.TokenType;
import types.PrimitiveType;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class VariableBlock extends Block<Variable> {
    private String type;

    private String name;

    private String assignOperator;

    private Value parsedValue = null;

    private ArrayList<Token> values;

    public VariableBlock(Block parentBlock, String line, int lineNumber, String type, String name, String assignOperator, ArrayList<Token> values) {
        super(parentBlock, line, lineNumber);

        this.type = type;

        this.name = name;

        this.values = values;

        this.assignOperator = assignOperator;

    }

    public String getType() {
        try {
            return Type.match(type, this).toString();
        } catch (Exception exception) {
            return type;
        }
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return parsedValue;
    }

    @Override
    public Variable run() {

        Type typeMatch = Type.match(type, this);

        if (PrimitiveType.VOID == typeMatch || null == typeMatch) {
            throw getIllegalStateException("Invalid variable type " + typeMatch);
        }

        for (Token token : values) {

            if (token.processed) {
                continue;
            }

            if (token.getTokenType() == TokenType.OPERATOR) {
                throw getIllegalStateException("Assign operators in variable declaration are not supported.");
            }

            parsedValue = token.getTokenValue(this, values);

        }

        if (null != parsedValue && false == parsedValue.getType().toString().equals(typeMatch.toString())) {
            throw getIllegalStateException("Invalid type, expected " + typeMatch + " got " + parsedValue.getType());
        }

        Variable variable = new Variable(getParent(), name, typeMatch, parsedValue);

        getParent().setVariable(variable);

        return variable;
    }
}
