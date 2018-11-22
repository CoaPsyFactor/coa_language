package core;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class Variable extends Value {

    private Block block;

    private String variableName;

    public Variable(Block block, String name, Type type, Object value) {
        super(type, value);

        this.block = block;

        this.variableName = name;
    }

    public Block getBlock() {
        return this.block;
    }

    public String getVariableName() {
        return this.variableName;
    }
}
