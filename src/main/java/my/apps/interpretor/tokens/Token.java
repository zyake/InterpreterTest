package my.apps.interpretor.tokens;


public class Token {

    private TokenTypes tokenType;

    private String text;

    public Token(TokenTypes tokenType, String text) {
        this.tokenType = tokenType;
        this.text = text;
    }

    public TokenTypes getTokenType() {
        return tokenType;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return tokenType + ", text=" + text;
    }
}