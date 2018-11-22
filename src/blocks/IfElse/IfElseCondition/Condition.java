package blocks.IfElse.IfElseCondition;

import core.Value;

import java.util.ArrayList;

/**
 * Created by zvekete on 8.1.2017..
 */
public class Condition {

    private ArrayList<Value> values;

    private String conditionOperator;

    public Condition(String conditionOperator, ArrayList<Value> values) {
        this.conditionOperator = conditionOperator;

        this.values = values;
    }

    public boolean isTrue() {
        for (Value value : values) {
            System.out.println("If Condition: " + value.getValue());
        }

        return true;
    }

}
