package com.javapatterns.command.undoconcept;

public class Invoker {
    /**
     * @link aggregation
     * @directed
     */
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void action() {
        command.execute();
    }
}
