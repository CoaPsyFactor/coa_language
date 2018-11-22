package parsers;

import core.Block;
import core.Parser;
import tokenizer.Tokenizer;

/**
 * Created by zvekete on 7.1.2017..
 */
public class CommentParser extends Parser {
    @Override
    public boolean shouldParse(String codeLine, int lineNumber) {
        return codeLine.matches("//.*");
    }

    @Override
    public Block parse(Block parentBlock, Tokenizer tokenizer) {
        return null;
    }
}
