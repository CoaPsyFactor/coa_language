package core.identifier.identifiers.functions.object;

import blocks.FunctionBlock;
import core.Block;
import core.Type;
import core.Value;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;
import types.PrimitiveType;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class FunctionIdentifier extends Identifier<Object> {

    private FunctionBlock functionBlock;

    public FunctionIdentifier(FunctionBlock functionBlock) {
        super(functionBlock.getFunctionName(), IdentifierType.FUNCTION);

        this.functionBlock = functionBlock;

        this.minArguments = functionBlock.getParametersCount();

        this.maxArguments = functionBlock.getParametersCount();

    }

    @Override
    public Object execute(Block executeBlock, ArrayList<Token> tokens) {
        ArrayList<Value> values = new ArrayList<>(tokens.size());

        for (Token token : tokens) {

            if (token.processed) {
                continue;
            }

            if (null == token) {
                values.add(new Value(Type.match("null", executeBlock), null));

                continue;
            }

            values.add(token.getTokenValue(executeBlock, tokens));

        }

        return functionBlock.invoke(values.toArray(new Value[]{}));
    }
}
