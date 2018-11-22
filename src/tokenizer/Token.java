package tokenizer;

import blocks.ClassBlock;
import core.Block;
import core.Type;
import core.Value;
import core.Variable;
import core.identifier.IdentifierHelper;
import core.identifier.identifiers.Identifiers;
import core.identifier.identifiers.statements.Null;
import types.PrimitiveType;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class Token {

    private String identifier;

    private TokenType type;

    public boolean processed = false;

    public Token(String tokenIdentifier, TokenType tokenType) {
        this.identifier = tokenIdentifier;

        this.type = tokenType;
    }

    public String getTokenIdentifier() {
        return this.identifier;
    }

    public TokenType getTokenType() {
        return this.type;
    }

    public static TokenType getTokenValueType(Object value)
    {
        if (value instanceof Character) {
            return TokenType.CHAR;
        } else if (value instanceof String) {
            return TokenType.STRING;
        } else if (value instanceof Integer || value instanceof Float) {
            return TokenType.NUMBER;
        } else if (value instanceof Boolean) {
            return TokenType.BOOLEAN;
        } else if (value instanceof Variable) {
            return TokenType.valueOf(((Variable) value).getType().toString());
        } else if (value instanceof ClassBlock) {
            return TokenType.IDENTIFIER;
        } else if (value instanceof Null) {

        }

        return TokenType.TOKEN;
    }

    public Value getTokenValue(Block parent, ArrayList<Token> tokens) {

        IdentifierHelper identifierHelper = new IdentifierHelper(parent, tokens);

        this.processed = true;

        switch (getTokenType()) {
            case TOKEN:
            case STRING:
            case BOOLEAN:
            case CONDITIONAL:
            case LOGICAL:

                return new Value(Type.match(getTokenType().toString(), parent), identifier);

            case NUMBER:

                if (identifier.contains(".")) {
                    return new Value(PrimitiveType.NUMBER, Float.valueOf(identifier));
                } else {
                    return new Value(PrimitiveType.NUMBER, Integer.valueOf(identifier));
                }

            case CHAR:

                return new Value(PrimitiveType.CHAR, identifier.charAt(0));

            case IDENTIFIER:

                Object newValue = identifierHelper.getIdentifierValue(identifier, parent.getLine(), parent.getLineNumber());

                if (newValue instanceof Value) {
                    return (Value) newValue;
                }

                return new Value(Type.match(getTokenValueType(newValue).toString(), parent), newValue);

            case VARIABLE:

                Variable variable = parent.getVariable(identifier);

                if (variable.getValue() instanceof Value) {
                    Value value = ((Value) variable.getValue());

                    return new Value(value.getType(), value.getValue());
                }

                return new Value(variable.getType(), variable.getValue());

            default:

                throw new IllegalStateException("Invalid variable value: " + type);
        }
    }

    @Override
    public String toString() {
        return this.identifier;
    }
}
