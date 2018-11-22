package core.identifier.identifiers.statements;

import core.Block;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;

import java.util.ArrayList;

/**
 * Created by zvekete on 6.1.2017..
 */
public class True extends Identifier<Boolean> {
    public True() {
        super("true", IdentifierType.STATEMENT);

        minArguments = 0;

        maxArguments = 0;
    }

    @Override
    protected Boolean execute(Block executeBlock, ArrayList<Token> tokens) {
        return true;
    }
}
