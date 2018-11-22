package core.identifier.identifiers.functions.number;

import core.Block;
import core.Type;
import core.Value;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;
import tokenizer.TokenType;
import types.PrimitiveType;

import java.util.ArrayList;

/**
 * Created by zvekete on 7.1.2017..
 */
public class SubNumbers extends Identifier<Float> {
    public SubNumbers() {
        super("sub", IdentifierType.FUNCTION);

        minArguments = 2;
    }

    @Override
    protected Float execute(Block executeBlock, ArrayList<Token> tokens) {

        Value baseValue = tokens.get(0).getTokenValue(executeBlock, tokens);

        validateType(executeBlock, baseValue);

        Float base = Float.valueOf(baseValue.getValue().toString());

        for (Token token : tokens) {

            if (token.processed) {
                continue;
            }

            Value tokenValue = token.getTokenValue(executeBlock, tokens);

            validateType(executeBlock, tokenValue);

            base -= Float.valueOf(token.getTokenIdentifier());
        }

        return base;
    }

    private void validateType(Block block, Value value) {
        if (false == value.getType().equals(PrimitiveType.NUMBER)) {
            throw new IllegalStateException(
                    "Invalid parameter type, expected number got " + value.getType() + "\nParameter " + value.getValue() +
                            "\nOn line " + block.getLineNumber() + "\n" + block.getLine()
            );
        }
    }
}
