package com.github.qlang.core.function;

import com.github.qlang.core.type.QObject;

public interface Function {
    QObject call(QObject[] args);
}
