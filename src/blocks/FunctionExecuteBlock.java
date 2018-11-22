package blocks;

import core.Block;
import core.Type;
import core.Value;
import core.Variable;
import core.identifier.Identifier;
import core.identifier.identifiers.Identifiers;
import tokenizer.Token;
import tokenizer.TokenType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class FunctionExecuteBlock extends Block<Identifier> {

    private String functionName;

    private ArrayList<Token> parameters;

    public FunctionExecuteBlock(Block parentBlock, String line, int lineNumber, String functionName, ArrayList<Token> parameters) {
        super(parentBlock, line, lineNumber);

        this.functionName = functionName;

        this.parameters = parameters;
    }

    @Override
    public Identifier run() {
        Identifier identifier = Identifiers.getIdentifier(functionName, this.getLine(), this.getLineNumber());

        ArrayList<Token> newParameters = new ArrayList<>();

        for (Token token : parameters) {

            if (token.processed) {
                continue;
            }

            Value value = token.getTokenValue(getParent(), parameters);

            Token parsedToken = null;

            if (null == value) {
                parsedToken = null;
            } else {
                parsedToken = new Token(value.getValue().toString(), TokenType.valueOf(value.getType().toString()));
            }

            if ((null != parsedToken && parsedToken.getTokenType() != TokenType.TOKEN) || null == parsedToken) {
                newParameters.add(parsedToken);
            }
        }

        identifier.invoke(this, newParameters);

        for (Token token : parameters) {
            token.processed = false;
        }

        return identifier;
    }
}
