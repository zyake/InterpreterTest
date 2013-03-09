package my.apps.interpretor.tokens;

import my.apps.interpretor.tokens.Token;

import java.util.List;

public interface Tokenizer {

    List<Token> tokenize(String text);
}
