package core.identifier.identifiers.functions.number;

import core.Block;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;
import tokenizer.TokenType;

import java.util.ArrayList;

/**
 * Created by zvekete on 6.1.2017..
 */
public class RoundNumber extends Identifier<Integer>
{
    public RoundNumber() {
        super("round", IdentifierType.FUNCTION);

        maxArguments = 1;

        minArguments = 1;
    }

    @Override
    protected Integer execute(Block executeBlock, ArrayList<Token> tokens) {

        Token number = tokens.get(0);

        if (number.getTokenType() != TokenType.NUMBER) {
            throw new IllegalArgumentException("Round argument must be number.\nOn line " + executeBlock.getLineNumber() + "\n" + executeBlock.getLine());
        }

        return Math.round(Float.valueOf(number.getTokenIdentifier()));
    }
}
