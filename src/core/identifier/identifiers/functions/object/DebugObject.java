package core.identifier.identifiers.functions.object;

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
public class DebugObject extends Identifier<Boolean> {

    public DebugObject() {
        super("debug", IdentifierType.FUNCTION);
    }

    @Override
    public Boolean execute(Block executeBlock, ArrayList<Token> tokens) {

        for (Token token : tokens) {

            if (token.processed) {
                continue;
            }

            Value value = token.getTokenValue(executeBlock, tokens);

            String type = value.getType().toString();

            int length = token.getTokenIdentifier().length();

            System.out.println(
                 type + " (" + length + ") " + value.getValue() );
        }

        return true;
    }
}
