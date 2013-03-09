package my.apps.interpretor.nodes;

import java.util.Map;

public class NullNode implements Node {
    @Override
    public String toString() {
        return "{ null }";
    }

    @Override
    public int evalute(Map<String, Integer> variable) {
        return 0;
    }
}
