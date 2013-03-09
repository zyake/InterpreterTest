package my.apps.interpretor.nodes;

import java.util.Map;

public interface Node {

    int evalute(Map<String, Integer> variable);
}
