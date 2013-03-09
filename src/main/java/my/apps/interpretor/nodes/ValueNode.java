package my.apps.interpretor.nodes;

import java.util.Map;

public class ValueNode implements Node {

    private int value;

    public ValueNode(int value) {
        this.value = value;
    }

    @Override
    public int evalute(Map<String, Integer> variable) {
        return value;
    }
}
