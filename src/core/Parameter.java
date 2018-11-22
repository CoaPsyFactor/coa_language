package core;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class Parameter {
    private String name;

    private Type type;

    public Parameter(Type type, String name) {
        this.type = type;

        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }
}
