package io.github.qdexlab.qlang.core.function;

import io.github.qdexlab.qlang.core.type.QObject;

public interface Function {
    QObject call(QObject[] args);
}
