package core.identifier.identifiers.functions.number;

import core.Block;
import core.Value;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;
import types.PrimitiveType;

import java.util.ArrayList;

/**
 * Created by zvekete on 7.1.2017..
 */
public class DivideNumbers extends Identifier<Float> {
    public DivideNumbers() {
        super("div", IdentifierType.FUNCTION);

        minArguments = 2;

        maxArguments = 2;
    }

    @Override
    protected Float execute(Block executeBlock, ArrayList<Token> tokens) {

        Float value = null;

        Float divideValue = null;

        for (Token token : tokens) {
            if (token.processed) {
                continue;
            }

            Value tokenValue = token.getTokenValue(executeBlock, tokens);

            if (false == tokenValue.getType().equals(PrimitiveType.NUMBER)) {
                throw new IllegalArgumentException("div arguments must be numbers.\nOn line " + executeBlock.getLineNumber() + "\n" + executeBlock.getLine());
            }

            if (null == value) {
                value = Float.valueOf(tokenValue.getValue().toString());

                continue;
            }

            divideValue = Float.valueOf(tokenValue.getValue().toString());
        }

        if (0 == divideValue) {
            throw new IllegalArgumentException("Cannot divide by zero.\nOn line " + executeBlock.getLineNumber() + "\n" + executeBlock.getLine());
        }

        return value / divideValue;
    }
}
