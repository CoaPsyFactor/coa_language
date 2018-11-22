package core.identifier.identifiers.functions.number;

import core.Block;
import core.Value;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;
import tokenizer.TokenType;
import types.PrimitiveType;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class SumNumbers extends Identifier<Float> {

    public SumNumbers() {
        super("sum", IdentifierType.FUNCTION);

        minArguments = 2;
    }

    @Override
    protected Float execute(Block executeBlock, ArrayList<Token> tokens) {

        Float total = 0f;

        for (Token token : tokens) {

            if (token.processed) {
                continue;
            }

            Value value = token.getTokenValue(executeBlock, tokens);

            validateType(executeBlock, value);

            total += Float.valueOf(value.getValue().toString());
        }

        return total;
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
