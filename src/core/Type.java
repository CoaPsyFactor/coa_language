package core;

import core.identifier.Identifier;
import core.identifier.IdentifierType;
import core.identifier.identifiers.Identifiers;
import types.PrimitiveType;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public interface Type {
    public static Type match(String typeIdentifier, Block block) {
        try {

            return PrimitiveType.valueOf(typeIdentifier.toUpperCase());

        } catch (IllegalArgumentException exception) {

            if (typeIdentifier.toUpperCase().equals("FLOAT") || typeIdentifier.toUpperCase().equals("INT")) {
                return PrimitiveType.NUMBER;
            }

            Identifier identifier = Identifiers.getIdentifier(typeIdentifier, block.getLine(), block.getLineNumber());

            return identifier.getType();

        }
    }
}
