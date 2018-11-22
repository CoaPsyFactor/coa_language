package parsers;

import blocks.FunctionBlock;
import core.AccessModifier;
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
public class FunctionParser extends Parser<FunctionBlock> {

    private String matchLineRegex = "(private|public|protected)\\s*function\\s*[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)\\s*\\:\\s*[a-zA-Z_][a-zA-Z0-9_]*";


    @Override
    public boolean shouldParse(String codeLine, int lineNumber) {

        this.line = codeLine;

        this.lineNumber = lineNumber;

        return codeLine.matches(matchLineRegex);
    }

    @Override
    public FunctionBlock parse(Block parentBlock, Tokenizer tokenizer) {
        String accessModifier = tokenizer.getNextToken().getTokenIdentifier();

        tokenizer.getNextToken(); // Skip "function"

        String functionName = tokenizer.getNextToken().getTokenIdentifier();

        tokenizer.getNextToken(); // Skip opening bracket

        Token nextToken = tokenizer.getNextToken();

        ArrayList<String[]> parameters = new ArrayList<>();

        if (false == this.isClosingBracket(nextToken)) {
            String[] parametersData = new String[]{nextToken.getTokenIdentifier(), null}; // 0: parameter type, 1: parameter value

            while (tokenizer.hasNext()) {
                Token currentToken = tokenizer.getNextToken();

                if (currentToken.getTokenType() == TokenType.TOKEN && currentToken.getTokenIdentifier().equals(",")) {
                    continue;
                }

                if (this.isClosingBracket(currentToken)) {
                    break;
                }

                if (null == parametersData[0]) { // We are looking for parameter type
                    parametersData[0] = currentToken.getTokenIdentifier();
                } else { // We are looking for its value
                    parametersData[1] = currentToken.getTokenIdentifier();

                    // Add parameter to list
                    parameters.add(parametersData);

                    // Reset parameters data for next parameter
                    parametersData = new String[2];
                }
            }
        }

        tokenizer.getNextToken(); // skip ":" for return type definition

        String returnType = tokenizer.getNextToken().getTokenIdentifier();

        return new FunctionBlock(parentBlock, line, lineNumber, functionName, AccessModifier.valueOf(accessModifier.toUpperCase()), returnType, parameters);
    }

    private boolean isClosingBracket(Token token) {
        return token.getTokenIdentifier().equals(")");
    }
}
