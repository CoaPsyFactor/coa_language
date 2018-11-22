package tokenizer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class Tokenizer {
    private ArrayList<Data> tokensData;

    private String code;

    private Token pushBackToken;

    private boolean pushBack;

    private String[] identifiers = {"\\(", "\\)", "\\:", "\\.", "\\,", "\""};

    private String[] operators = {
            "\\=\\+", "\\=\\-", "\\=\\/", "\\=\\*", "\\=\\^",
            "\\=", "\\+", "\\-", "\\*", "\\/", "\\^"
    };

    private String[] logicals = {
            "\\|\\|", "\\&\\&"
    };

    private String[] conditionals = {
        "\\=\\=", "\\!\\=", "\\<", "\\>", "\\<\\=", "\\>\\="
    };

    public Tokenizer(String code) {
        this.code = code;

        this.loadAvailableTokenData();
    }

    private void loadAvailableTokenData() {

        this.tokensData = new ArrayList<>();

        this.tokensData.add(new Data(Pattern.compile("^([a-zA-Z_][a-zA-Z0-9_]*)"), TokenType.IDENTIFIER));

        this.tokensData.add(new Data(Pattern.compile("^(\'.\')"), TokenType.CHAR));

        this.tokensData.add(new Data(Pattern.compile("^([0-9]+\\.[0-9]+)"), TokenType.NUMBER));

        this.tokensData.add(new Data(Pattern.compile("^((-)?[0-9]+)"), TokenType.NUMBER));

        this.tokensData.add(new Data(Pattern.compile("^\"([^\"]*)\""), TokenType.STRING));

        this.tokensData.add(new Data(Pattern.compile("^(\\$[a-zA-Z_][a-zA-Z0-9_]*)"), TokenType.VARIABLE));

        for (String conditional : conditionals) {
            this.tokensData.add(new Data(Pattern.compile("^(" + conditional + ")"), TokenType.CONDITIONAL));
        }

        for (String logical : logicals) {
            this.tokensData.add(new Data(Pattern.compile("^(" + logical + ")"), TokenType.LOGICAL));
        }

        for (String identifier : identifiers) {
            this.tokensData.add(new Data(Pattern.compile("^(" + identifier + ")"), TokenType.TOKEN));
        }

        for (String operator : operators) {
            this.tokensData.add(new Data(Pattern.compile("^(" + operator + ")"), TokenType.OPERATOR));
        }

    }

    public Token getNextToken() {
        this.code = this.code.trim();

        if (this.shouldPushBack()) {
            this.pushBack = false;

            return this.pushBackToken;
        }

        if (this.code.isEmpty()) {
            return (this.pushBackToken = new Token("", TokenType.EMPTY));
        }

        for (Data tokenData : this.tokensData) {
            Matcher match = tokenData.getTokenPattern().matcher(this.code);

            if (false == match.find()) {
                continue;
            }

            String tokenIdentifier = match.group().trim();

            this.code = match.replaceFirst("");

            if (TokenType.STRING == tokenData.getTokenType() || TokenType.CHAR == tokenData.getTokenType()) {
                tokenIdentifier = tokenIdentifier.substring(1, tokenIdentifier.length() - 1);
            }

            return (this.pushBackToken = new Token(tokenIdentifier, tokenData.getTokenType()));
        }

        throw new IllegalStateException("Unable to parse " + this.code);
    }

    public boolean hasNext() {
        return false == this.code.isEmpty();
    }

    public void pushBack() {
        this.pushBack = null != this.pushBackToken;
    }

    public ArrayList<Data> getTokensData() {
        return this.tokensData;
    }

    public String getCode() {
        return this.code;
    }

    public Token getPushBackToken() {
        return this.pushBackToken;
    }

    public boolean shouldPushBack() {
        return this.pushBack;
    }
}
