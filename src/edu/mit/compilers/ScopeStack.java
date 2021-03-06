package edu.mit.compilers;

import edu.mit.compilers.ir.Ir;
import edu.mit.compilers.ir.IrType;

import java.util.Hashtable;
import java.util.Stack;

/**
 * Created by devinmorgan on 10/10/16.
 */
public class ScopeStack {
    private final Stack<ScopeStack.SymbolTable> stack;

    public ScopeStack() {
        this.stack = new Stack<ScopeStack.SymbolTable>();
    }

    public void addObjectToCurrentScope(String id, Ir object) {
        this.stack.peek().hashtable.put(id,object);
    }

    public boolean checkIfSymbolExistsAtAnyScope(String id) {
        for( SymbolTable e = this.stack.peek(); e != null; e = e.parentScope ) {
            Ir found = e.hashtable.get(id);
            if (found != null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkIfSymbolExistsAtCurrentScope(String id) {
        return this.stack.peek().hashtable.containsKey(id);
    }

    public void addSymbolToGlobalScope(String id, Ir object) {
        // get the parent-most (global) scope
        SymbolTable globalScope = this.stack.peek();
        while (globalScope.parentScope != null) {
            globalScope = globalScope.parentScope;
        }

        globalScope.hashtable.put(id, object);
    }

    public boolean checkIfSymbolExistsInGlobalScope(String id) {
        // get the parent-most (global) scope
        SymbolTable globalScope = this.stack.peek();
        while (globalScope.parentScope != null) {
            globalScope = globalScope.parentScope;
        }

        return globalScope.hashtable.containsKey(id);
    }

    public Ir getSymbol(String id) {
        for (SymbolTable e = this.stack.peek(); e != null; e = e.parentScope) {
            Ir found = e.hashtable.get(id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public SymbolTable deleteCurrentScope() {
        if (this.stack.size() > 0) {
            this.stack.pop();
        }
        return null;
    }

    public SymbolTable createNewBlockScope() {
        // start the ScopeStack out with a Global Scope
        if (this.stack.size() == 0) {
            ScopeStack.SymbolTable globalScope = this.new SymbolTable();
            this.stack.add(globalScope);
            return globalScope;
        }
        // add child scopes if a Global Scope already exists
        else {
            ScopeStack.SymbolTable parentScope = this.stack.peek();
            ScopeStack.SymbolTable newScope = this.new SymbolTable(parentScope);
            this.stack.push(newScope);
            return newScope;
        }
    }

    public SymbolTable createNewMethodScope(IrType methodType) {
        ScopeStack.SymbolTable parentScope = this.stack.peek();
        ScopeStack.SymbolTable newScope = this.new SymbolTable(parentScope, methodType);
        this.stack.push(newScope);
        return newScope;
    }

    public SymbolTable createNewLoopScope() {
        ScopeStack.SymbolTable parentScope = this.stack.peek();
        ScopeStack.SymbolTable newScope = this.new SymbolTable(parentScope, true);
        this.stack.push(newScope);
        return newScope;
    }

    public IrType getScopeReturnType() {
        for (SymbolTable e = this.stack.peek(); e != null; e = e.parentScope) {
            IrType methodType = e.scopeReturnType;
            if (methodType != null) {
                return methodType;
            }
        }
        return null;
    }

    public boolean isScopeForALoop() {
        for (SymbolTable e = this.stack.peek(); e != null; e = e.parentScope) {
            boolean isALoop = e.isLoop;
            if (isALoop) {
                return true;
            }
        }
        return false;
    }

    class SymbolTable {
        protected Hashtable<String, Ir> hashtable;
        protected SymbolTable parentScope;
        protected IrType scopeReturnType;
        protected boolean isLoop = false;

        protected SymbolTable(){
            this.hashtable = new Hashtable<String, Ir>();
            this.parentScope = null;
        }

        protected SymbolTable(SymbolTable parentScope) {
            this.hashtable = new Hashtable<String, Ir>();
            this.parentScope = parentScope;
        }

        protected SymbolTable(SymbolTable parentScope, IrType scopeReturnType) {
            this.hashtable = new Hashtable<String, Ir>();
            this.parentScope = parentScope;
            this.scopeReturnType = scopeReturnType;
        }

        protected SymbolTable(SymbolTable parentScope, boolean isLoop) {
            this.hashtable = new Hashtable<String, Ir>();
            this.parentScope = parentScope;
            this.isLoop = isLoop;
        }
    }

}


