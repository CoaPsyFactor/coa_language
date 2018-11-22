package core.identifier.identifiers.functions.object;

import blocks.ClassBlock;
import core.Block;
import core.identifier.Identifier;
import core.identifier.IdentifierType;
import tokenizer.Token;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class ClassIdentifier extends Identifier<Object> {

    private ClassBlock classBlock;

    public ClassIdentifier(ClassBlock block) {
        super(block.getClassName(), IdentifierType.CLASS);

        classBlock = block;
    }

    @Override
    public Object execute(Block executeBlock, ArrayList<Token> tokens) {
        return classBlock;
    }
}
