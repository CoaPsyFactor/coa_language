package parsers;

import blocks.IfElse.IfElseBlock;
import blocks.IfElse.IfElseBlockType;
import core.Block;
import core.Parser;
import tokenizer.Token;
import tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class IfElseParser extends Parser<IfElseBlock> {

    private static final HashMap<IfElseBlockType, String> patterns;

    static {
        patterns = new HashMap<>();

        patterns.put(IfElseBlockType.IF, "^(if\\s*\\(.+\\))$");

        patterns.put(IfElseBlockType.ELSE_IF, "^(elseif\\s*\\(.*\\))$");

        patterns.put(IfElseBlockType.ELSE, "^(else)$");

        patterns.put(IfElseBlockType.END_IF, "^(endif)$");
    }

    private IfElseBlockType type = null;

    @Override
    public boolean shouldParse(String codeLine, int lineNumber) {

        this.line = codeLine;

        this.lineNumber = lineNumber;

        final ArrayList<IfElseBlockType> types = new ArrayList<>(1);

        patterns.forEach((type, pattern) -> {
            if (codeLine.matches(pattern)) {
                types.add(type);
            }
        });

        if (false == types.isEmpty()) {
            type = types.get(0);
        }

        return false == types.isEmpty();
    }

    @Override
    public IfElseBlock parse(Block parentBlock, Tokenizer tokenizer) {

        IfElseBlock parent = null;

        if (parentBlock instanceof IfElseBlock) {
            parent = (IfElseBlock) parentBlock;
        }

        if (IfElseBlockType.IF != type && null != parent) {
            if (IfElseBlockType.ELSE_IF == type && (parent.getType() != IfElseBlockType.IF && parent.getType() != IfElseBlockType.ELSE_IF)) {
                throw new IllegalStateException("Invalid elseif statement.");
            } else if (IfElseBlockType.ELSE == type && (parent.getType() != IfElseBlockType.IF && parent.getType() != IfElseBlockType.ELSE_IF)) {
                throw new IllegalStateException("Invalid else statement.");
            }
        } else if (null == parent) {
            if (IfElseBlockType.ELSE == type) {
                throw new IllegalStateException("Invalid else statement.");
            } else if (IfElseBlockType.ELSE_IF == type) {
                throw new IllegalStateException("Invalid elseif statement.");
            } else if (IfElseBlockType.END_IF == type) {
                throw new IllegalStateException("Invalid endif statement.");
            }
        }

        IfElseBlock ifElseBlockBlock = null;

        if (IfElseBlockType.ELSE == type) {
            return new IfElseBlock(parentBlock, line, lineNumber, type, new ArrayList<>());
        }

        return new IfElseBlock(parentBlock, line, lineNumber, type, this.parseIfStatement(tokenizer));
    }

    private ArrayList<Token> parseIfStatement(Tokenizer tokenizer) {
        tokenizer.getNextToken().getTokenIdentifier(); // Skip "if" "elseif"

        ArrayList<Token> conditionTokens = new ArrayList<>();

        int open = 0;

        while (tokenizer.hasNext()) {

            Token currentToken = tokenizer.getNextToken();

            if (currentToken.getTokenIdentifier().equals("(")) {
                open++;
            } else if (currentToken.getTokenIdentifier().equals(")") && open > 0) {
                open--;
            } else if (currentToken.getTokenIdentifier().equals(")") && open == 0) {
                throw new IllegalStateException("Missing opening bracket.");
            }

            conditionTokens.add(currentToken);

        }

        if (open != 0) {
            throw new IllegalStateException("Missing closing bracket.");
        }

        return conditionTokens;
    }
}
