package core.identifier.identifiers.statements;

import blocks.FunctionBlock;
import core.Block;
import core.Value;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zvekete on 6.1.2017..
 */
public class Return extends Identifier<Value> {

    public Return() {
        super("return", IdentifierType.STATEMENT);

        minArguments = 1;

        maxArguments = 1;
    }

    @Override
    public Value execute(Block executeBlock, ArrayList<Token> tokens) {

        FunctionBlock parentBlock = executeBlock.getParentFunctionBlock();

        if (null == parentBlock) {
            throw new IllegalStateException("Return statement must be inside function block.");
        }

        Value returnValue = tokens.get(0).getTokenValue(executeBlock, tokens);

        parentBlock.setReturnValue(returnValue);

        return returnValue;
    }
}
