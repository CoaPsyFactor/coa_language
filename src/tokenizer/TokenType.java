package tokenizer;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public enum TokenType {

    /**
     * Nothing
     */
    EMPTY,

    /**
     * Token is for example operator equals, or open/closed bracket
     */
    TOKEN,

    /**
     * First letter then letter, number or underscore
     */
    IDENTIFIER,

    CONDITIONAL,

    LOGICAL,

    /**
     * Starts with $
     */
    VARIABLE,

    /**
     * Operators
     */
    OPERATOR,

    /**
     * Number value
     */
    NUMBER,

    /** True/False */
    BOOLEAN,

    /**
     * Anything between double quotes
     */
    STRING,

    /**
     * One letter between single quotes
     */
    CHAR,

    VOID
}