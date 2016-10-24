package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

/**
 * Created by abel on 10/5/16.
 */
public abstract class IrOperBinary extends  IrOper {
    public String operation;
    public IrExpr leftOperand;
    public IrExpr rightOperand;
    public IrOperBinary(String operation, IrExpr leftOperand, IrExpr rightOperand){
        super(leftOperand.getLineNumber(), leftOperand.getColNumber());
        this.operation = operation;
        //Swap the left and right and left operands if they are in the form of <Literal OP Expr>
        if(leftOperand instanceof IrLiteral && !(rightOperand instanceof IrLiteral)){
            Ir temp = rightOperand;
            rightOperand = leftOperand;
            leftOperand = (IrExpr) temp;
        }
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }
    public String getOperation(){
        return this.operation;
    }
   public String toString(){
       return leftOperand.toString() + " " +  operation + " "+ rightOperand.toString();
   }

    // Expects the inheriting classes to have this method implemented.
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        return "";
    }

}

