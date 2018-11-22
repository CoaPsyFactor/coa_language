package core.identifier.identifiers.statements;

import core.Block;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;

import java.util.ArrayList;

/**
 * Created by zvekete on 7.1.2017..
 */
public class Null extends Identifier<Null> {
    public Null() {
        super("null", IdentifierType.STATEMENT);
    }

    @Override
    protected Null execute(Block executeBlock, ArrayList<Token> tokens) {
        return null;
    }
}
