package com.brunopbrito31.apilivros.models.enums;

public enum Operation {
    ADD(0),
    REM(1),
    MUL(2),
    DIV(3);

    private Integer selectedOperation;

    private Operation(Integer operation) {
        this.selectedOperation = operation;
    }
}
