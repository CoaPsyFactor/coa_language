import blocks.ClassBlock;
import blocks.FunctionBlock;
import blocks.FunctionExecuteBlock;
import blocks.IfElse.IfElseBlock;
import blocks.IfElse.IfElseBlockType;
import blocks.VariableBlock;
import core.Block;
import core.Parser;
import core.identifier.Identifier;
import core.identifier.identifiers.Identifiers;
import core.identifier.identifiers.functions.number.*;
import core.identifier.identifiers.functions.object.ClassIdentifier;
import core.identifier.identifiers.functions.object.DebugObject;
import core.identifier.identifiers.functions.object.FunctionIdentifier;
import core.identifier.identifiers.functions.string.Echo;
import core.identifier.identifiers.functions.string.StringConcatIdentifier;
import core.identifier.identifiers.statements.False;
import core.identifier.identifiers.statements.Null;
import core.identifier.identifiers.statements.Return;
import core.identifier.identifiers.statements.True;
import parsers.*;
import tokenizer.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Aleksandar Zivanovic
 * @email coapsyfactor@gmail.com
 */
public class Runtime {
    private ClassBlock mainClass = null;

    private FunctionBlock mainFunction = null;

    private Block currentBlock = null;

    private ClassBlock currentClassBlock = null;

    private FunctionBlock currentFunctionBlock = null;

    private IfElseBlock currentIfElseBlock = null;

    private ArrayList<ClassBlock> classes = new ArrayList<>();

    private Parser<?>[] parsers = new Parser<?>[]{
            new ClassParser(),
            new FunctionParser(),
            new VariableParser(),
            new IfElseParser(),
            new BlockEndParser(),
            new FunctionExecuteParser(),
            new CommentParser()
    };

    private Identifier<?>[] identifiers = new Identifier<?>[]{
            // Statements
            new False(),
            new True(),
            new Null(),
            new Return(),

            // String Manipulation Functions
            new StringConcatIdentifier(),

            // Numbers Manipulation Functions
            new SumNumbers(),
            new RoundNumber(),
            new MultiplyNumbers(),
            new DivideNumbers(),
            new SubNumbers(),

            // Variable/string/numbers... functions
            new Echo(),
            new DebugObject(),

    };

    public Runtime(String loadFile) throws Exception {

        for (Identifier identifier : identifiers) {
            Identifiers.addIdentifier(identifier);
        }

        int lineNumber = 0;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(loadFile));

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            lineNumber++;

            line = line.trim();

            if (line.length() == 0) {
                continue;
            }

            try {
                if (false == executeLine(line, parsers, lineNumber)) {
                    throw new IllegalArgumentException("Can not parse on line " + lineNumber + " (\"" + line + "\")");
                }
            } catch (Exception exception) {
                exception.printStackTrace();

                throw new IllegalStateException("Error on line " + lineNumber + " (" + line + ")");
            }
        }

        if (null == mainClass || null == mainFunction) {
            throw new IllegalStateException("Missing startup function.");
        }

        for (Block child : mainClass.getChildren()) {
            if (false == child instanceof VariableBlock) {
                continue;
            }

            child.run();
        }

        mainFunction.invoke();

    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing entry point.");
        }

        try {
            new Runtime(args[0]);
        } catch (Exception exception) {
//            System.out.println("Runtime Exception:\n" + exception.getMessage());
            exception.printStackTrace();
        }

    }

    private boolean executeLine(String line, Parser<?>[] parsers, int lineNumber) {
        boolean executionSuccess;

        line = line.trim();

        if (line.isEmpty()) {
            return true;
        }

        Tokenizer tokenizer = new Tokenizer(line);

        executionSuccess = false;

        for (Parser<?> parser : parsers) {
            if (false == parser.shouldParse(line, lineNumber)) {
                continue;
            }

            if ((executionSuccess = parser instanceof CommentParser)) {
                break;
            }

            if ((executionSuccess = parser instanceof BlockEndParser)) {

                if (null == currentBlock) {
                    throw new IllegalStateException("Invalid end statement");
                }

                FunctionBlock openFunctionBlock = currentBlock.getOpenFunctionBlock();

                if (null != openFunctionBlock) {
                    openFunctionBlock.isClosed = true;

                    currentBlock = openFunctionBlock.getParent();

                    break;
                }

                ClassBlock openClassBlock = currentBlock.getOpenClassBlock();

                if (null != openClassBlock) {
                    openClassBlock.isClosed = true;

                    currentBlock = null;
                } else {
                    throw new IllegalStateException("Invalid end statement");
                }

                break;
            }

            Block newBlock = parser.parse(currentBlock, tokenizer);

            executionSuccess = true;

            if (newBlock instanceof ClassBlock) {

                if (currentBlock != null && currentBlock.getOpenClassBlock() != null) {
                    throw new IllegalStateException("Unclosed class block.");
                }

                ClassBlock classBlock = (ClassBlock) newBlock;

                classes.add((classBlock));

                currentClassBlock = classBlock;

                Identifiers.addIdentifier(new ClassIdentifier(classBlock));

            } else if (newBlock instanceof FunctionBlock) {

                FunctionBlock functionBlock = (FunctionBlock) newBlock;

                if (functionBlock.getFunctionName().equals("_startup")) {

                    mainClass = newBlock.getParentClass();

                    mainFunction = functionBlock;

                }

                if (currentBlock.getOpenFunctionBlock() != null) {
                    throw new IllegalStateException("Function block not closed.");
                }

                currentFunctionBlock = functionBlock;

                currentBlock.getParentClass().addChild(newBlock);

                Identifiers.addIdentifier(new FunctionIdentifier(functionBlock));

            } else if (newBlock instanceof IfElseBlock) {
                IfElseBlock ifElseBlockBlock = (IfElseBlock) newBlock;

                switch (ifElseBlockBlock.getType()) {
                    case IF:

                        currentBlock.addChild(newBlock);

                        break;
                    case ELSE_IF:
                    case ELSE:
                        IfElseBlock ifBlock = currentBlock.getOpenIfBlock();

                        if (null == ifBlock) {
                            throw new IllegalStateException("Invalid elseif/else statement.");
                        }

                        if (ifElseBlockBlock.getType() == IfElseBlockType.ELSE_IF) {
                            ifBlock.addElseIf(ifElseBlockBlock);
                        } else {
                            currentBlock.getOpenIfBlock().setElse(ifElseBlockBlock);
                        }

                        break;
                    case END_IF:

                        currentBlock.getOpenIfBlock().isClosed = true;

                        newBlock = currentBlock.getParent();

                        break;
                }

                currentIfElseBlock = ifElseBlockBlock;
            } else if (null != currentBlock) {
                currentBlock.addChild(newBlock);
            }

            if (newBlock instanceof VariableBlock || newBlock instanceof FunctionExecuteBlock) {
                newBlock = newBlock.getParent();
            }

            currentBlock = newBlock;

            break;
        }

        return executionSuccess;
    }
}
