package blocks.IfElse.IfElseCondition.group;

import blocks.IfElse.IfElseCondition.Condition;

/**
 * Created by zvekete on 8.1.2017..
 */
public class OrGroup extends Group {
    public OrGroup(Group parent, Condition leftConditionResult, Condition rightConditionResult) {
        super(parent, leftConditionResult, rightConditionResult);
    }

    @Override
    protected boolean run() {
//        return getLeftConditionResult().equals(Condition.SUCCESS) || getRightConditionResult().equals(Condition.SUCCESS);
        return false;
    }


}
