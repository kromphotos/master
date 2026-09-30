package com.kristina.gwttreecrud.shared;

public class TreeCrudProgramException extends Exception {
    public TreeCrudProgramException() {
        super();
    }
    
    public TreeCrudProgramException(String message) {
        super(message);
    }
    
    public TreeCrudProgramException(String message, Throwable cause) {
        super(message, cause);
    }

}
