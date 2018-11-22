package core.identifier.identifiers.functions.string;

import core.Block;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;

import java.util.ArrayList;

/**
 * Created by zvekete on 6.1.2017..
 */
public class StringPosition extends Identifier<Integer> {
    public StringPosition() {
        super("strpos", IdentifierType.FUNCTION);

        this.minArguments = 2;

        this.maxArguments = 2;
    }

    @Override
    public Integer execute(Block executeBlock, ArrayList<Token> tokens) {
        return null;
    }
}
