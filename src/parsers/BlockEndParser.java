package parsers;

import core.Block;
import core.Parser;
import tokenizer.Tokenizer;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class BlockEndParser extends Parser {

    @Override
    public boolean shouldParse(String codeLine, int lineNumber) {

        this.line = codeLine;

        this.lineNumber = lineNumber;

        return codeLine.matches("^(end)\\s*$");
    }

    @Override
    public Block parse(Block parentBlock, Tokenizer tokenizer) {

        if (null != parentBlock.getParent()) {
            return parentBlock.getParent();
        }

        return parentBlock;
    }
}
