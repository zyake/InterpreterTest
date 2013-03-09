package my.apps.interpretor;

import my.apps.interpretor.nodes.Node;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class DefaultExpressionParserTest {

    @Test
    public void testParse_normal_singleMultiply() throws Exception {
        String exp = "abc * cde";
        Node node = new DefaultExpressionParser().parse(exp);
        Map<String, Integer> contextMap = new HashMap<String, Integer>();
        contextMap.put("abc", 10);
        contextMap.put("cde", 11);

        int result = node.evalute(contextMap);
        assertEquals(110, result);
    }


    @Test
    public void testParse_normal_doubleMultiply() throws Exception {
        String exp = "abc * cde * fgh";
        Node node = new DefaultExpressionParser().parse(exp);
        Map<String, Integer> contextMap = new HashMap<String, Integer>();
        contextMap.put("abc", 2);
        contextMap.put("cde", 3);
        contextMap.put("fgh", 5);

        int result = node.evalute(contextMap);
        assertEquals(30, result);
    }


    @Test
    public void testParse_normal_plusAndMultiply() throws Exception {
        String exp = "abc + cde * fgh";
        Node node = new DefaultExpressionParser().parse(exp);
        Map<String, Integer> contextMap = new HashMap<String, Integer>();
        contextMap.put("abc", 2);
        contextMap.put("cde", 3);
        contextMap.put("fgh", 5);

        int result = node.evalute(contextMap);
        assertEquals(17, result);
    }

    @Test
    public void testParse_normal_plusAndMultiply2() throws Exception {
        String exp = "a + b + c * d";
        Node node = new DefaultExpressionParser().parse(exp);
        Map<String, Integer> contextMap = new HashMap<String, Integer>();
        contextMap.put("a", 2);
        contextMap.put("b", 3);
        contextMap.put("c", 5);
        contextMap.put("d", 4);

        System.out.println(node);
        int result = node.evalute(contextMap);
        assertEquals(25, result);
    }

    @Test
    public void testParse_normal_plusAndMultiply3() throws Exception {
        String exp = "a * b + c * d";
        Node node = new DefaultExpressionParser().parse(exp);
        Map<String, Integer> contextMap = new HashMap<String, Integer>();
        contextMap.put("a", 2);
        contextMap.put("b", 3);
        contextMap.put("c", 5);
        contextMap.put("d", 4);

        System.out.println(node);
        int result = node.evalute(contextMap);
        assertEquals(26, result);
    }


}
