package parsers;

import blocks.FunctionExecuteBlock;
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
public class FunctionExecuteParser extends Parser {

    @Override
    public boolean shouldParse(String codeLine, int lineNumber) {

        this.line = codeLine;

        this.lineNumber = lineNumber;

        return codeLine.matches("^([a-zA-Z\\_][a-zA-Z0-9\\_]*\\s*\\(.*\\))$");
    }

    @Override
    public Block parse(Block parentBlock, Tokenizer tokenizer) {

        Token functionName = tokenizer.getNextToken();

        ArrayList<Token> parameters = new ArrayList<>();

        boolean nextParameter = true;

        while (tokenizer.hasNext()) {
            Token token = tokenizer.getNextToken();

            if (token.getTokenType() == TokenType.TOKEN && token.getTokenIdentifier().equals("(") || token.getTokenIdentifier().equals(",")) {
                nextParameter = true;

                parameters.add(new Token(token.getTokenIdentifier(), token.getTokenType()));

                continue;
            }

            if (false == nextParameter && token.getTokenType() != TokenType.TOKEN) {
                throw new IllegalArgumentException("Invalid number of arguments\nOn line " + lineNumber + "\n" + line);
            }

            parameters.add(new Token(token.getTokenIdentifier(), token.getTokenType()));

            nextParameter = false;
        }

        if (nextParameter) {
            throw new IllegalArgumentException("Invalid number of arguments\nOn line " + lineNumber + "\n" + line);
        }

        return new FunctionExecuteBlock(parentBlock, line, lineNumber, functionName.getTokenIdentifier(), parameters);
    }
}
