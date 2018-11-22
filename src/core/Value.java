package core;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class Value {
    private Object value;

    private Type type;

    public Value(Type type, Object value) {
        this.type = type;

        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }
}
