package com.mycompany.task1.commandline;

/**
 * CommandLine manager interface
 * @author Fabrizio Faustinoni
 */
public interface ICommandLine {
    
    public int getInputSeconds() throws MissingArgumentException;
    
}
