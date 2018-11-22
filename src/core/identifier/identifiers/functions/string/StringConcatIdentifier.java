package core.identifier.identifiers.functions.string;

import core.Block;
import core.Value;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class StringConcatIdentifier extends Identifier<String> {

    public StringConcatIdentifier() {
        super("concat", IdentifierType.FUNCTION);

        minArguments = 1;
    }

    @Override
    protected String execute(Block executeBlock, ArrayList<Token> tokens) {

        String string = "";

        for (Token token : tokens) {
            Value value = token.getTokenValue(executeBlock, tokens);

            string += value.getValue();
        }

        return string;
    }
}
