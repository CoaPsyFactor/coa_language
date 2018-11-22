package core.identifier;

import core.Block;
import tokenizer.Token;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public abstract class Identifier<T> {
    private IdentifierType type;

    private String name;

    protected int minArguments = 0;

    protected int maxArguments = Integer.MAX_VALUE;

    public Identifier(String name, IdentifierType type) {
        this.name = name;

        this.type = type;
    }

    public String getName() {
        return name;
    }

    public IdentifierType getType() {
        return type;
    }

    public T invoke(Block executeBlock, ArrayList<Token> tokens) {

        if (tokens.size() < minArguments) {
            throw new IllegalArgumentException(name + " requires at least " + minArguments + " argument(s) got " + tokens.size() + "\nOn line " + executeBlock.getLineNumber() + "\n" + executeBlock.getLine());
        }

        if (maxArguments != Integer.MAX_VALUE && tokens.size() > maxArguments) {
            throw new IllegalArgumentException(name + " accepts maximum " + maxArguments + " argument(s) got " + tokens.size() + "\nOn line " + executeBlock.getLineNumber() + "\n" + executeBlock.getLine());
        }

        return this.execute(executeBlock, tokens);
    }

    protected abstract T execute(Block executeBlock, ArrayList<Token> tokens);
}
