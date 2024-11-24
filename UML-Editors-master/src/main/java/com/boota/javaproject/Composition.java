package com.boota.javaproject;

public class Composition {
    private Class sourceClass;
    private Class targetClass;

    public Composition(Class sourceClass, Class targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    public Class getSourceClass() {
        return sourceClass;
    }

    public Class getTargetClass() {
        return targetClass;
    }
}
