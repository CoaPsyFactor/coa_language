package core.identifier.identifiers.functions.number;

import core.Block;
import core.Value;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;
import types.PrimitiveType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zvekete on 7.1.2017..
 */
public class MultiplyNumbers extends Identifier<Float> {
    public MultiplyNumbers() {
        super("mul", IdentifierType.FUNCTION);

        minArguments = 2;
    }

    @Override
    protected Float execute(Block executeBlock, ArrayList<Token> tokens) {
        float number = 1f;

        for (Token token : tokens)  {
            if (token.processed) {
                continue;
            }

            Value value = token.getTokenValue(executeBlock, tokens);

            if (false == value.getType().equals(PrimitiveType.NUMBER)) {
                throw new IllegalArgumentException("mul arguments must be numbers.\nOn line " + executeBlock.getLineNumber() + "\n" + executeBlock.getLine());
            }

            number *= Float.valueOf(value.getValue().toString());
        }

        return number;
    }
}
