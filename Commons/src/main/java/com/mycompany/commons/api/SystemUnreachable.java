package com.mycompany.commons.api;

public class SystemUnreachable  extends Exception{
    
    public SystemUnreachable(String message) {
        super(message);
    }
    
    public SystemUnreachable(String messagem, Throwable cause) {
        super(messagem, cause);
    }
}
