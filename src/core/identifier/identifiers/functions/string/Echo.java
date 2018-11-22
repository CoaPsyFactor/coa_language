package core.identifier.identifiers.functions.string;

import core.Block;
import core.Value;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class Echo extends Identifier<Boolean> {

    public Echo() {
        super("echo", IdentifierType.FUNCTION);

        minArguments = 1;

        maxArguments = 1;
    }

    @Override
    public Boolean execute(Block executeBlock, ArrayList<Token> tokens) {

        Value value = tokens.get(0).getTokenValue(executeBlock, tokens);

        System.out.println(value.getValue());

        return true;
    }
}
