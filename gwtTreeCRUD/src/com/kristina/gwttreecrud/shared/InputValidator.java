package com.kristina.gwttreecrud.shared;

public class InputValidator {
    private static final String ERROR_EMPTY_NAME = "Node name can not be empty";
    private static final String ERROR_EMPTY_IP = "IP address can not be empty";
    private static final String ERROR_EMPTY_PORT = "Port can not be empty";
    private static final String ERROR_EMPTY_PARENT_ID = "Parent ID can not be empty";

    private static final String ERROR_PORT_NOT_NUMBER = "Port must be a number";
    private static final String ERROR_PORT_TOO_LONG = "Port must contain no more than 5 digits";
    private static final String ERROR_PORT_OUT_OF_RANGE = "Port must be in the range of 0 to 65535";

    private static final String ERROR_IP_TOO_LONG = "IP address must contain no more than 15 characters";

    private static final String ERROR_PARENT_ID_NOT_NUMBER = "Parent ID must be a number";

    private static final int MAX_IP_LENGTH = 15;
    private static final int MAX_PORT_LENGTH = 5;
    private static final int MIN_PORT = 1;
    private static final int MAX_PORT = 65535;

    private InputValidator() {
    }

    public static String validateName(String name) throws TreeCrudProgramException {
        if (name == null || name.trim().isEmpty()) {
            throw new TreeCrudProgramException(ERROR_EMPTY_NAME);
        }
     
        return name;
    }

    public static String validateIp(String ip) throws TreeCrudProgramException {
        if (ip == null || ip.trim().isEmpty()) {
            throw new TreeCrudProgramException(ERROR_EMPTY_IP);
        }
        
        if (ip.length() > MAX_IP_LENGTH) {
            throw new TreeCrudProgramException(ERROR_IP_TOO_LONG);
        }
        
        return ip;
    }

    public static Integer validatePort(String port) throws TreeCrudProgramException {
        if (port == null || port.trim().isEmpty()) {
            throw new TreeCrudProgramException(ERROR_EMPTY_PORT);
        }
        
        if (port.length() > MAX_PORT_LENGTH) {
            throw new TreeCrudProgramException(ERROR_PORT_TOO_LONG);
        }

        Integer portInt;
        try {
            portInt = Integer.valueOf(port);
        } catch (NumberFormatException e) {
            throw new TreeCrudProgramException(ERROR_PORT_NOT_NUMBER);
        }

        if (portInt < MIN_PORT || portInt > MAX_PORT) {
            throw new TreeCrudProgramException(ERROR_PORT_OUT_OF_RANGE);
        }
        return portInt;
    }

    public static Long validateParentId(String parentId) throws TreeCrudProgramException {
        if (parentId == null || parentId.trim().isEmpty()) {
            throw new TreeCrudProgramException(ERROR_EMPTY_PARENT_ID);
        }
        
        try {
            return Long.valueOf(parentId);
        } catch (NumberFormatException e) {
            throw new TreeCrudProgramException(ERROR_PARENT_ID_NOT_NUMBER);
        }
    }

}
