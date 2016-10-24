package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

import java.util.Hashtable;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStatement extends Ir {
    public IrStatement(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    // Expects the inheriting classes to have this method implemented.
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        return "";
    }
}
