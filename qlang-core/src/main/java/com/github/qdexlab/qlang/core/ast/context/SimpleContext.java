package com.github.qdexlab.qlang.core.ast.context;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.type.QObject;

import java.util.HashMap;
import java.util.Map;

public class SimpleContext implements Context {

    private final Context parent;
    private final Map<String, QObject> variableTable = new HashMap<>();

    public SimpleContext() {
        this(null);
    }

    public SimpleContext(Context parent) {
        this.parent = parent;
    }

    @Override
    public QObject getVariable(String variable) {
        if (variableTable.containsKey(variable)) {
            return variableTable.get(variable);
        }
        if (parent != null) {
            return parent.getVariable(variable);
        }
        return null;
    }

    @Override
    public void setVariable(String variable, QObject value) {
        variableTable.put(variable, value);
    }
}
