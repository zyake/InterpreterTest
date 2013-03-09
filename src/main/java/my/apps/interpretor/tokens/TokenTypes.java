package my.apps.interpretor.tokens;

public enum TokenTypes {
    Variable, Value, Operator, StartParentheses, EndParentheses;

    public static boolean isStartParentheses(char c) {
        boolean isParentheses = c == '(';

        return isParentheses;
    }

    public static boolean isEndParentheses(char c) {
        boolean isParentheses = c == ')';

        return isParentheses;
    }
}
