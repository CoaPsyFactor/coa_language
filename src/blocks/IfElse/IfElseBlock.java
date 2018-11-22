package blocks.IfElse;

import blocks.IfElse.IfElseCondition.Condition;
import blocks.IfElse.IfElseCondition.ConditionGroup;
import blocks.IfElse.IfElseCondition.group.Group;
import core.Block;
import core.Value;
import tokenizer.Token;
import types.PrimitiveType;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class IfElseBlock extends Block {

    public boolean isClosed = false;
    private IfElseBlockType type;
    private ArrayList<IfElseBlock> elses;
    private IfElseBlock elseCondition;
    private IfElseBlock parentIfElseBlock;
    private ArrayList<Token> conditionTokens;

    public IfElseBlock(Block parentBlock, String line, int lineNumber, IfElseBlockType type, ArrayList<Token> conditionTokens) {
        super(parentBlock, line, lineNumber);

        elses = new ArrayList<>();

        this.type = type;

        this.conditionTokens = conditionTokens;
    }

    @Override
    public Object run() {

        ArrayList<ConditionGroup> conditionGroups = new ArrayList<>();

        ConditionGroup currentGroup = null;

        loop: for (int tokenIndex = 0, tokenIndexMax = conditionTokens.size(); tokenIndex < tokenIndexMax; tokenIndex++) {
            Token conditionToken = conditionTokens.get(tokenIndex);

            if (conditionToken.processed) {
                continue;
            }

            Value tokenValue = conditionToken.getTokenValue(getParent(), conditionTokens);

            ArrayList<Value> values = new ArrayList<>();

            if (tokenValue.getType().equals(PrimitiveType.TOKEN)) {
                String tokenValueIdentifier = tokenValue.getValue().toString();

                switch (tokenValueIdentifier) {
                    case "(":

                        currentGroup = new ConditionGroup(currentGroup);

                        continue loop;
                    case ")":

                        if (null == currentGroup) {
                            break loop;
                        }

                        conditionGroups.add(currentGroup);

                        currentGroup = currentGroup.getParent();

                        break;
                }
            }

            if (tokenValue.getType().equals(PrimitiveType.CONDITIONAL)) {

                ArrayList<Value> conditionValues = new ArrayList<>();

                Token left = conditionTokens.get(tokenIndex - 1);

                Token right = conditionTokens.get(tokenIndex + 1);

                conditionValues.add(left.getTokenValue(this, conditionTokens));

                conditionValues.add(right.getTokenValue(this, conditionTokens));

                currentGroup.addCondition(new Condition(tokenValue.getValue().toString(), conditionValues));

            }
/*
            if (tokenValue.getType().equals(PrimitiveType.LOGICAL)) {
                String tokenValueIdentifier = tokenValue.getValue().toString();

                switch (tokenValueIdentifier) {
                    case "\\|\\|": // OR " || "
                        break;
                    case "\\&\\&": // AND " && "
                        break;
                }
            }
            */
        }

        for (IfElseBlock ifElseBlock : elses) {
            ifElseBlock.run();
        }

        if (null != elseCondition) {
            elseCondition.run();
        }

        for (Block block : getChildren()) {
            block.run();
        }

        return null;
    }

    public IfElseBlockType getType() {
        return type;
    }

    public void addElseIf(IfElseBlock subIf) {
        elses.add(subIf);
    }

    public void setElse(IfElseBlock elseBlock) {
        elseCondition = elseBlock;
    }

    public ArrayList<IfElseBlock> getIfElses() {
        return elses;
    }

    public IfElseBlock getElseCondition() {
        return elseCondition;
    }
}
