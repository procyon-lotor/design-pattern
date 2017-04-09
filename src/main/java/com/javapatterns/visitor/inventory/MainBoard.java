package com.javapatterns.visitor.inventory;

public class MainBoard extends Equipment {
    public void accept(Visitor v) {
        System.out.println("MainBoard has been visited.");
        v.visitMainBoard(this);
    }

    public double price() {
        return 100.00;
    }
}

