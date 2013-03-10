package my.apps.interpretor;

import my.apps.interpretor.nodes.*;
import my.apps.interpretor.tokens.DefaultTokenizer;
import my.apps.interpretor.tokens.Token;
import my.apps.interpretor.tokens.TokenTypes;
import my.apps.interpretor.tokens.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DefaultExpressionParser implements ExpressionParser {

    protected Tokenizer tokenizer = new DefaultTokenizer();

    @Override
    public Node parse(String text) {
        List<Token> tokens = tokenizer.tokenize(text);
        return doParse(tokens);
    }

    protected Node doParse(List<Token> tokens) {
        Stack<Token> tokenStack = new Stack<Token>();
        Stack<Node> nodeStack = new Stack<Node>();
        for ( int i = 0 ; i < tokens.size() ; i ++ ) {
            Token currentToken = tokens.get(i);
            TokenTypes currentTokenType = currentToken.getTokenType();
            Node recursiveNode = null;
            if ( TokenTypes.StartParentheses == currentToken.getTokenType() ) {
                List<Token> recursiveTokens = new ArrayList<Token>();
                i ++;
                int level = 0;
                while ( ! ( TokenTypes.EndParentheses == tokens.get(i).getTokenType() && level == 0 ) ) {
                    boolean endParentheseNotFound = i + 1 == tokens.size();
                    if ( endParentheseNotFound ) {
                        throw new RuntimeException("end parenthese not found: " + tokens);
                    }

                    if ( TokenTypes.StartParentheses == tokens.get(i).getTokenType() ) {
                        level ++;
                    }

                    if ( TokenTypes.EndParentheses == tokens.get(i).getTokenType() ) {
                        level --;
                    }

                    recursiveTokens.add(tokens.get(i));
                    i ++;
                }

                // parse parentheses recursively
                recursiveNode = doParse(recursiveTokens);

                // use parentheses as variable
                currentTokenType = TokenTypes.Variable;
            }

            if ( TokenTypes.Variable == currentTokenType ) {
                handleVariable(tokenStack, nodeStack, currentToken, recursiveNode);
                continue;
            }

            if ( TokenTypes.Operator == currentTokenType ) {
                tokenStack.push(currentToken);
            }
        }

        boolean canConstructDifferedVars = tokenStack.size() > 0;
        if ( canConstructDifferedVars ) {
            constructDifferedVars(tokenStack, nodeStack);
        }

        return nodeStack.pop();
    }

    private void constructDifferedVars(Stack<Token> tokenStack, Stack<Node> nodeStack) {
        Node lastNode = nodeStack.peek();
        Node currentNode = null;
        while ( ! tokenStack.isEmpty() ) {
            Node rightVarNode = null;
            boolean requireNewNode = currentNode == null;
            if  ( requireNewNode ) {
                Token rightVarToken = tokenStack.pop();
                rightVarNode = NodeFactory.createVariableNode(rightVarToken.getText());
            } else {
                rightVarNode = currentNode;
            }

            Token opeToken = tokenStack.pop();
            OperatorNode opeNode = NodeFactory.createOperator(opeToken.getText());

            Node leftVarNode = null;
            boolean useLastNode = tokenStack.isEmpty();
            if ( useLastNode ) {
                leftVarNode = lastNode;
            } else {
                Token leftVarToken = tokenStack.pop();
                leftVarNode = NodeFactory.createVariableNode(leftVarToken.getText());
            }

            opeNode.setLeft(leftVarNode);
            opeNode.setRight(rightVarNode);
            currentNode = opeNode;
        }

        nodeStack.push(currentNode);
    }

    private void handleVariable(Stack<Token> tokenStack, Stack<Node> nodeStack, Token token, Node recursiveNode) {
        if ( nodeStack.isEmpty() ) {
            if ( recursiveNode != null ) {
                nodeStack.push(recursiveNode);
            } else {
                Node variableNode = NodeFactory.createVariableNode(token.getText());
                nodeStack.push(variableNode);
            }
            return;
        }

        Token opeToken = tokenStack.peek();
        if ( NodeFactory.requireDifferedEvaluation(opeToken.getText()) ) {
            tokenStack.push(token);
            return;
        }

        tokenStack.pop();
        boolean existsVarToken = tokenStack.size() > 0 && tokenStack.peek().getTokenType() == TokenTypes.Variable;
        OperatorNode opeNode = NodeFactory.createOperator(opeToken.getText());
        Node leftNode = null;
        Node rightNode = recursiveNode == null ? NodeFactory.createVariableNode(token.getText()) : recursiveNode;
        if ( existsVarToken ) {
            Token leftVarToken = tokenStack.pop();
            leftNode = NodeFactory.createVariableNode(leftVarToken.getText());
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

        return;
    }

    private void createNodesOnTokenStack(Stack<Node> nodeStack, Stack<Token> tokenStack) {
        if ( tokenStack.isEmpty() ) {
            // 直接ノードスタック上の最上位ノードとの合成ノードを作成する。
            Node topNode = nodeStack.pop();
            Token topOpeToken = tokenStack.pop();
            OperatorNode conjuctionOpeNode = NodeFactory.createOperator(topOpeToken.getText());
            conjuctionOpeNode.setLeft(nodeStack.peek());
            conjuctionOpeNode.setRight(topNode);
            nodeStack.push(topNode);
            nodeStack.push(conjuctionOpeNode);

            return;
        }

        while ( ! tokenStack.isEmpty() ) {
            Node rightNode = nodeStack.pop();
            Token opeToken = tokenStack.pop();
            OperatorNode opeNode = NodeFactory.createOperator(opeToken.getText());

            Node leftNode = null;
            boolean useExistingNode = tokenStack.isEmpty();
            if ( useExistingNode ) {
                leftNode = nodeStack.peek();
            } else {
                Token leftToken = tokenStack.pop();
                leftNode = NodeFactory.createVariableNode(leftToken.getText());
            }
            opeNode.setLeft(leftNode);
            opeNode.setRight(rightNode);
            nodeStack.push(opeNode);
        }
    }
}
