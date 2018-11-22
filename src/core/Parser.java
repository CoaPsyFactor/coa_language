package core;

import tokenizer.Tokenizer;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public abstract class Parser<T extends Block> {

    protected String line;

    protected int lineNumber;

    /**
     * Checks whether or not line should be parsed
     *
     * @param codeLine Line of code that needs to be parsed
     * @return Should or should not line be parsed
     */
    public abstract boolean shouldParse(String codeLine, int lineNumber);

    /**
     * @param parentBlock Parent block
     * @param tokenizer   Tokenizer
     * @return Parse value
     */
    public abstract T parse(Block parentBlock, Tokenizer tokenizer);
}
