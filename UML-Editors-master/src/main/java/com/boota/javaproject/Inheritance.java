package com.boota.javaproject;

public class Inheritance {
    private Class sourceClass; // Represents the child class
    private Class targetClass; // Represents the parent class

    public Inheritance(Class sourceClass, Class targetClass) {
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
