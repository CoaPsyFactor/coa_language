package core.identifier;

import core.Block;
import core.Value;
import core.Variable;
import core.identifier.identifiers.Identifiers;
import tokenizer.Token;
import tokenizer.TokenType;

import java.util.ArrayList;

/**
 * Created by zvekete on 6.1.2017..
 */
public class IdentifierHelper {

    private ArrayList<Token> parameters;

    private Block parent;

    public IdentifierHelper(Block parent, ArrayList<Token> parameters) {

        this.parent = parent;

        this.parameters = parameters;

    }

    public Object getIdentifierValue(String identifierName, String line, int lineNumber)  {
        Identifier identifier = Identifiers.getIdentifier(identifierName, line, lineNumber);

        switch (identifier.getType()) {
            case CLASS:
                throw new IllegalStateException("Class invoking not yet implemented");

            case FUNCTION:
            case STATEMENT:

                ArrayList<Token> parameters = getParameters();

                if (openBrackets != 0) {
                    throw new IllegalArgumentException("Invalid arguments format.\nOn line " + lineNumber + "\n" + line);
                }

                return identifier.invoke(parent, parameters);

        }

        return null;
    }

    private int openBrackets = 0;

    public ArrayList<Token> getParameters() {
        ArrayList<Token> returnParameters = new ArrayList<>();

        boolean newParameter = true;

        for (Token token : parameters) {
            if (token.processed) {
                continue;
            }

            token.processed = true;

            if (token.getTokenType() == TokenType.TOKEN && token.getTokenIdentifier().equals("(")) {
                openBrackets++;

                newParameter = true;

                continue;
            }

            if (token.getTokenType() == TokenType.TOKEN && token.getTokenIdentifier().equals(")")) {

                if (newParameter && returnParameters.size() > 0) {
                    throw new IllegalArgumentException("Invalid arguments format.\nOn line " + parent.getLineNumber() + "\n" + parent.getLine());
                }

                openBrackets--;

                break;
            }

            if (token.getTokenType() == TokenType.TOKEN && token.getTokenIdentifier().equals(",")) {
                if (newParameter) {
                    throw new IllegalArgumentException("Invalid arguments format. Maybe there is comma.");
                }

                newParameter = true;

                continue;
            }

            if (false == newParameter) {
                throw new IllegalArgumentException("Invalid arguments format. Maybe missing comma.");
            }

            Token newToken = null;

            Value value = token.getTokenValue(parent, parameters);

            returnParameters.add(new Token(value.getValue().toString(), TokenType.valueOf(value.getType().toString())));

            newParameter = false;
        }

        return returnParameters;
    }
}
