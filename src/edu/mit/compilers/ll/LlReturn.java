package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlReturn extends LlStatement {
    private final LlComponent returnValue;

    public LlReturn(LlComponent returnValue) {
        this.returnValue = returnValue;
    }
}