package com.mycompany.task.commandline;

/**
 * CommandLine Implementation
 * @author Fabrizio Faustinoni
 */
public class CommandLine implements ICommandLine {

    private String[] args;

    public CommandLine(String[] args) {
        this.args = args;
    }

    @Override
    public int getInputSeconds() throws MissingArgumentException {
        if (args == null || args.length < 3) {
            throw new MissingArgumentException("The 3 argument must be a positive integer");
        }

        try {
            return Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            throw new MissingArgumentException("The 3 argument must be a positive integer");
        }
    }

}
