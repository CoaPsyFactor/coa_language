package core.identifier.identifiers.statements;

import core.Block;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;

import java.util.ArrayList;

/**
 * Created by zvekete on 6.1.2017..
 */
public class False extends Identifier<Boolean> {
    public False() {
        super("false", IdentifierType.STATEMENT);
    }

    @Override
    protected Boolean execute(Block executeBlock, ArrayList<Token> tokens) {
        return false;
    }
}
