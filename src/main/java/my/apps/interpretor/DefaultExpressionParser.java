package my.apps.interpretor;

import my.apps.interpretor.nodes.*;
import my.apps.interpretor.tokens.DefaultTokenizer;
import my.apps.interpretor.tokens.Token;
import my.apps.interpretor.tokens.TokenTypes;
import my.apps.interpretor.tokens.Tokenizer;

import java.util.List;
import java.util.Stack;

public class DefaultExpressionParser implements ExpressionParser {

    protected Tokenizer tokenizer = new DefaultTokenizer();

    @Override
    public Node parse(String text) {
        List<Token> tokens = tokenizer.tokenize(text);
        Stack<Token> tokenStack = new Stack<Token>();
        Stack<Node> nodeStack = new Stack<Node>();
        for ( Token token : tokens ) {
            if ( TokenTypes.Variable == token.getTokenType() ) {
                if ( nodeStack.isEmpty() ) {
                    Node variableNode = createVariableNode(token.getText());
                    nodeStack.push(variableNode);
                    continue;
                }

                Token opeToken = tokenStack.peek();
                if ( OperatorFactory.requireDifferedEvaluation(opeToken.getText()) ) {
                    tokenStack.push(token);
                    continue;
                }

                    tokenStack.pop();
                    boolean existsVarToken = tokenStack.size() > 0 && tokenStack.peek().getTokenType() == TokenTypes.Variable;
                    OperatorNode opeNode = OperatorFactory.createOperator(opeToken.getText());
                    Node leftNode = null;
                    Node rightNode = createVariableNode(token.getText());
                    if ( existsVarToken ) {
                        Token leftVarToken = tokenStack.pop();
                    leftNode = createVariableNode(leftVarToken.getText());
                } else {
                    leftNode = nodeStack.peek();
                }
                opeNode.setLeft(leftNode);
                opeNode.setRight(rightNode);
                nodeStack.push(opeNode);

                boolean canConstructDifferedVars = tokenStack.size() > 0;
                if ( canConstructDifferedVars ) {
                    createNodesOnTokenStack(nodeStack, tokenStack);
                }

                continue;
            }

            if ( TokenTypes.Operator == token.getTokenType() ) {
                tokenStack.push(token);
            }
        }

        return nodeStack.pop();
    }

    private void createNodesOnTokenStack(Stack<Node> nodeStack, Stack<Token> tokenStack) {
        if ( tokenStack.isEmpty() ) {
            // 直接ノードスタック上の最上位ノードとの合成ノードを作成する。
            Node topNode = nodeStack.pop();
            Token topOpeToken = tokenStack.pop();
            OperatorNode conjuctionOpeNode = OperatorFactory.createOperator(topOpeToken.getText());
            conjuctionOpeNode.setLeft(nodeStack.peek());
            conjuctionOpeNode.setRight(topNode);
            nodeStack.push(topNode);
            nodeStack.push(conjuctionOpeNode);

            return;
        }

        while ( ! tokenStack.isEmpty() ) {
            Node rightNode = nodeStack.pop();
            Token opeToken = tokenStack.pop();
            OperatorNode opeNode = OperatorFactory.createOperator(opeToken.getText());

            Node leftNode = null;
            boolean useExistingNode = tokenStack.isEmpty();
            if ( useExistingNode ) {
                leftNode = nodeStack.peek();
            } else {
                Token leftToken = tokenStack.pop();
                leftNode = createVariableNode(leftToken.getText());
            }
            opeNode.setLeft(leftNode);
            opeNode.setRight(rightNode);
            nodeStack.push(opeNode);
        }
    }

    private Node createVariableNode(String text) {
        return new VariableNode(text);
    }
}
