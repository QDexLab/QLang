package com.github.qdexlab.qlang.core.function;

import com.github.qdexlab.qlang.core.type.QObject;

public interface Function {
    QObject call(QObject[] args);
}
