package core.identifier.identifiers;

import core.identifier.Identifier;

import java.util.HashMap;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public abstract class Identifiers {
    private static final HashMap<String, Identifier<?>> identifiers = new HashMap<>();

    public static void addIdentifier(Identifier<?> identifier) {

        if (identifiers.containsKey(identifier.getName())) {
            throw new IllegalStateException("Identifier " + identifier.getName() + " already registered.");
        }

        identifiers.put(identifier.getName(), identifier);
    }

    public static Identifier<?> getIdentifier(String name, String line, int lineNumber) throws IllegalStateException {
        if (false == identifiers.containsKey(name)) {
            throw new IllegalStateException("Identifier " + name + " not found.\nOn line " + lineNumber + "\n" + line);
        }

        return identifiers.get(name);
    }
}
