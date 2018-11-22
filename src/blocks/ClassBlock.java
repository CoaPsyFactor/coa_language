package blocks;

import core.Block;
import core.Type;
import tokenizer.Token;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class ClassBlock extends Block<Token> implements Type {

    public ClassBlock extendClass = null;
    public boolean isClosed = false;
    private String className;

    public ClassBlock(String name, String line, int lineNumber) {
        super(null, line, lineNumber);

        this.className = name;

    }

    public ClassBlock getExtendClass() {
        return extendClass;
    }

    public boolean isSubclassOf(ClassBlock classBlock) {
        ClassBlock currentClassBlock = this;

        String className = currentClassBlock.getClassName();

        while (null != currentClassBlock) {
            if (className.equals(currentClassBlock.getClassName())) {
                return true;
            }

            currentClassBlock = currentClassBlock.getParentClass();
        }

        return false;
    }

    public String getClassName() {
        return this.className;
    }

    @Override
    public Token run() {
        // execute constructor method
        return null;
    }

}
