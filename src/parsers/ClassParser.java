package parsers;

import blocks.ClassBlock;
import core.Block;
import core.Parser;
import tokenizer.Tokenizer;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class ClassParser extends Parser<ClassBlock> {

    private String codeLine;

    @Override
    public boolean shouldParse(String codeLine, int lineNumber) {

        this.line = codeLine;

        this.lineNumber = lineNumber;

        this.codeLine = codeLine;

        return codeLine.matches("class [a-zA-Z_][a-zA-Z0-9_]*");
    }

    @Override
    public ClassBlock parse(Block parentBlock, Tokenizer tokenizer) {
        tokenizer.getNextToken(); // Jump over "class" token

        if (false == tokenizer.hasNext()) {
            throw new IllegalStateException("Missing class name on line " + this.codeLine);
        }

        String className = tokenizer.getNextToken().getTokenIdentifier(); // Get name of defined class

        return new ClassBlock(className, line, lineNumber);
    }
}
