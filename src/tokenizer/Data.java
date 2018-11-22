package tokenizer;

import java.util.regex.Pattern;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class Data {

    private Pattern pattern;

    private TokenType type;

    public Data(Pattern tokenPattern, TokenType tokenType) {
        this.pattern = tokenPattern;

        this.type = tokenType;
    }

    public Pattern getTokenPattern() {
        return this.pattern;
    }

    public TokenType getTokenType() {
        return this.type;
    }
}
