package blocks.IfElse.IfElseCondition;

import java.util.ArrayList;

/**
 * Created by zvekete on 9.1.2017..
 */
public class ConditionGroup {

    private ConditionGroup parent;

    private ArrayList<Condition> conditions;

    public ConditionGroup(ConditionGroup parent) {
        this.parent = parent;

        this.conditions = new ArrayList<>();
    }

    public ConditionGroup getParent() {
        return parent;
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }
}