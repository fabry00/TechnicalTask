package com.mycompany.task1.commandline;

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
