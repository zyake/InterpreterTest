package my.apps.interpretor.tokens;

import my.apps.interpretor.nodes.OperatorFactory;

import java.util.ArrayList;
import java.util.List;

public class DefaultTokenizer implements Tokenizer {

    @Override
    public List<Token> tokenize(String text) {
        List<Token> tokens = new ArrayList<Token>();
        int pos = 0;
        while ( pos < text.length() ) {
            boolean isStartVariable = Character.isAlphabetic(text.charAt(pos));
            if ( isStartVariable ) {
                int start = pos;
                while ( pos < text.length() && text.charAt(pos) != ' ' ) {
                    pos ++;
                }
                String variable = text.substring(start, pos);
                Token varToken = createVarToken(variable);
                tokens.add(varToken);
                continue;
            }

            boolean isOperator = OperatorFactory.isOperator(text.charAt(pos));
            if ( isOperator ) {
                Token opToken = createOperatorToken(text.charAt(pos));
                tokens.add(opToken);
                pos ++;
                continue;
            }

            boolean isStartParenthese = TokenTypes.isStartParentheses(text.charAt(pos));
            if ( isStartParenthese ) {
                Token parToken = createParentheseToken(TokenTypes.StartParentheses, '(');
                tokens.add(parToken);
                pos ++;
                continue;
            }

            boolean isEndParenthese = TokenTypes.isEndParentheses(text.charAt(pos));
            if ( isEndParenthese ) {
                Token parToken = createParentheseToken(TokenTypes.EndParentheses, ')');
                tokens.add(parToken);
                pos ++;
                continue;
            }

            pos ++;
        }

        return tokens;
    }

    private Token createParentheseToken(TokenTypes startParentheses, char c) {
        return new Token(startParentheses, Character.toString(c));
    }

    private Token createOperatorToken(char c) {
        return new Token(TokenTypes.Operator, Character.toString(c));
    }

    private Token createVarToken(String variable) {
        return new Token(TokenTypes.Variable, variable);
    }
}
