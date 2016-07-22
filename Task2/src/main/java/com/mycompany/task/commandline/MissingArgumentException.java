package com.mycompany.task.commandline;

/**
 * CommandLine exception
 *
 * @author Fabrizio Faustinoni
 */
public class MissingArgumentException extends Exception {

    public MissingArgumentException(String message) {
        super(message);
    }

}
