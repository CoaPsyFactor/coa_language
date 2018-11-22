package parsers;

import blocks.VariableBlock;
import core.Block;
import core.Parser;
import tokenizer.Token;
import tokenizer.TokenType;
import tokenizer.Tokenizer;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class VariableParser extends Parser {

    @Override
    public boolean shouldParse(String codeLine, int lineNumber) {

        this.line = codeLine;

        this.lineNumber = lineNumber;

        return codeLine.matches("([a-zA-Z_][a-zA-Z0-9_]*) \\$([a-zA-Z_][a-zA-Z0-9_]*)\\s*([\\=\\+\\-\\*\\/]+)\\s*\"?(.*)\"?");
    }

    @Override
    public Block parse(Block parentBlock, Tokenizer tokenizer) {
        String type = tokenizer.getNextToken().getTokenIdentifier();

        String name = tokenizer.getNextToken().getTokenIdentifier();

        String operator = tokenizer.getNextToken().getTokenIdentifier();

        ArrayList<Token> parameters = new ArrayList<>();

        boolean nextParameter = true;

        int open = 0;

        while (tokenizer.hasNext()) {
            Token token = tokenizer.getNextToken();

            if (token.getTokenType() == TokenType.TOKEN && token.getTokenIdentifier().equals("(")) {
                open++;
            } else if (token.getTokenType() == TokenType.TOKEN && token.getTokenIdentifier().equals(")")) {
                open--;
            }

            if (token.getTokenType() == TokenType.TOKEN && token.getTokenIdentifier().equals("(") || token.getTokenIdentifier().equals(",")) {
                nextParameter = true;

                parameters.add(token);

                continue;
            }

            if (false == nextParameter && token.getTokenType() != TokenType.TOKEN) {
                throw new IllegalArgumentException("Unable to parse.\nOn line " + lineNumber + "\n" + line);
            }

            parameters.add(token);

            nextParameter = false;
        }

        if (open != 0) {
            throw new IllegalArgumentException("Missing some brackets\nOn line " + lineNumber + "\n" + line);
        }

        if (nextParameter) {
            throw new IllegalArgumentException("Invalid number of arguments\nOn line " + lineNumber + "\n" + line);
        }

        return new VariableBlock(parentBlock, line, lineNumber, type, name, operator, parameters);
    }
}
