package core;

import blocks.ClassBlock;
import blocks.FunctionBlock;
import blocks.IfElse.IfElseBlock;
import blocks.IfElse.IfElseBlockType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public abstract class Block<T> {
    private Block parent;

    private ArrayList<Block> children = new ArrayList<>();

    private HashMap<String, Variable> variables = new HashMap<>();

    protected String line;

    protected int lineNumber;

    public Block(Block parentBlock, String line, int lineNumber) {
        this.parent = parentBlock;

        this.line = line;

        this.lineNumber = lineNumber;
    }

    public void addChild(Block childBlock) {
        this.children.add(childBlock);
    }

    private Variable findVariable(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }

        return null;
    }

    public Variable getVariable(String name) {
        Block block = this;

        Variable variable = findVariable(name);

        while (block != null && variable == null) {
            block = block.getParent();

            if (null != block) {
                variable = block.getVariable(name);
            }
        }

        if (variable == null) {
            throw new IllegalStateException("Variable " + name + " not defined.");
        }

        return variable;
    }

    public void setVariable(Variable variable) {
        variables.put(variable.getVariableName(), variable);
    }

    public Block getParent() {
        return this.parent;
    }

    public ClassBlock getParentClass() {
        Block current = this;

        while (null != current) {
            if (current instanceof ClassBlock) {
                return (ClassBlock) current;
            }

            current = current.getParent();
        }

        return null;
    }

    public Block[] getChildren() {
        return this.children.toArray(new Block[this.children.size()]);
    }

    public ArrayList<Block> getBlockTree() {
        ArrayList<Block> blocks = new ArrayList<>();

        Block block = this;

        while (null != block) {

            blocks.add(block);

            block = block.getParent();

        }

        Collections.reverse(blocks);

        return blocks;
    }

    public FunctionBlock getParentFunctionBlock()
    {
        Block block = this;

        while (null != block) {
            if (false == block instanceof FunctionBlock) {
                block = block.getParent();

                continue;
            }

            return  (FunctionBlock) block;
        }

        return null;
    }

    public IfElseBlock getOpenIfBlock() {
        Block block = this;

        while (null != block) {
            if (false == block instanceof IfElseBlock) {
                block = block.getParent();

                continue;
            }

            IfElseBlock ifElseBlockBlock = (IfElseBlock) block;

            if (ifElseBlockBlock.getType() == IfElseBlockType.IF && false == ifElseBlockBlock.isClosed) {
                return ifElseBlockBlock;
            }

            block = block.getParent();
        }

        return null;
    }

    public FunctionBlock getOpenFunctionBlock() {
        Block block = this;

        while (null != block) {
            if (false == block instanceof FunctionBlock) {
                block = block.getParent();

                continue;
            }

            FunctionBlock functionBlock = (FunctionBlock) block;

            if (false == functionBlock.isClosed) {
                return functionBlock;
            }

            block = block.getParent();
        }

        return null;
    }

    public ClassBlock getOpenClassBlock() {
        Block block = this;

        while (null != block) {
            if (false == block instanceof ClassBlock) {
                block = block.getParent();

                continue;
            }

            ClassBlock classBlock = (ClassBlock) block;

            if (false == classBlock.isClosed) {
                return classBlock;
            }

            block = block.getParent();
        }

        return null;
    }

    public IllegalStateException getIllegalStateException(String message)
    {
        return new IllegalStateException(message + "\nOn line " + lineNumber + "\n" + line) ;
    }

    public IllegalStateException getIllegalStateException(String message, Exception previous) {
        return new IllegalStateException(message + "\nOn line " + lineNumber + "\n" + line, previous) ;
    }

    public String getLine()
    {
        return line;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public abstract T run();

}
