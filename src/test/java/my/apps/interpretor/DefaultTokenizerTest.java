package my.apps.interpretor;

import my.apps.interpretor.tokens.DefaultTokenizer;
import my.apps.interpretor.tokens.Token;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DefaultTokenizerTest {

    @Test
    public void testTokenize_normal() throws Exception {
        String exp = "avg + bg / cdds * sss";
        List<Token> tokens= new DefaultTokenizer().tokenize(exp);

        assertEquals(tokens.toString(),
                "[Variable, text=avg, Operator, text=+, Variable, text=bg, Operator, text=/, Variable, text=cdds, Operator, text=*, Variable, text=sss]");
    }

    @Test
    public void testTokenize_normal_withParentheses() throws Exception {
        String exp = "( avg + bg ) / cdds * sss";
        List<Token> tokens= new DefaultTokenizer().tokenize(exp);

        assertEquals(tokens.toString(),
                "[StartParentheses, text=(, Variable, text=avg, Operator, text=+, Variable, text=bg, EndParentheses, text=), Operator, text=/, Variable, text=cdds, Operator, text=*, Variable, text=sss]");
    }
}
