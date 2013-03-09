package my.apps.interpretor.nodes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OperatorFactory {

    protected static final Set<Character> OPERATORS;

    static {
        Set<Character> ops = new HashSet<Character>();
        ops.add('*');
        ops.add('/');
        ops.add('+');
        ops.add('-');
        OPERATORS = Collections.unmodifiableSet(ops);
    }

    public static boolean isOperator(char text) {
        return OPERATORS.contains(text);
    }
    public static boolean requireDifferedEvaluation(String text) {
        return requireDifferedEvaluation(text.charAt(0));
    }

    public static boolean requireDifferedEvaluation(char text) {
        return '+' == text || '-' == text;
    }
    public static OperatorNode createOperator(String text) {
        return createOperator(text.charAt(0));
    }

    public static OperatorNode createOperator(char text) {
        if ( '*' == text ) {
            return new MultiplyNode();
        }
        if ( '/' == text ) {
            return new DivideNode();
        }
        if ( '+' == text ) {
            return new PlusNode();
        }
        if ( '-' == text ) {
            return new MinusNode();
        }
        throw  new RuntimeException("指定された操作に対応するクラスが見つかりません: " + text);
    }
}
