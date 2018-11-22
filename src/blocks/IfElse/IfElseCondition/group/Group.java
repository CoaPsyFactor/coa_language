package blocks.IfElse.IfElseCondition.group;

import blocks.IfElse.IfElseCondition.Condition;

import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public abstract class Group {
    private Condition leftConditionResult;

    private Condition rightConditionResult;

    private Group parent;

    private ArrayList<Group> children;

    public Group(Group parent, Condition leftConditionResult, Condition rightConditionResult) {
        this.parent = parent;

        this.leftConditionResult = leftConditionResult;

        this.rightConditionResult = rightConditionResult;

        children = new ArrayList<>();
    }

    public void addChild(Group child) {
        children.add(child);
    }

    public Condition getLeftConditionResult() {
        return leftConditionResult;
    }

    public Condition getRightConditionResult() {
        return rightConditionResult;
    }

    public boolean execute() {
        for (Group child : children) {
            child.execute();
        }

        return this.run();
    }

    public Group getParent() {
        return parent;
    }

    protected abstract boolean run();
}
