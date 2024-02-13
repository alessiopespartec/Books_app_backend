package com.example.demo.exceptions;

public class MessageFactory {

    public static String emptyOrNullFieldMessage(String entityName, String fieldName) {
        String message = fieldName.toLowerCase() + " of " + entityName.toLowerCase() + " cannot be empty or null";
        return Character.toUpperCase(message.charAt(0)) + message.substring(1);
    }

    public static String fieldErrorMessage(String fieldName) {
        return fieldName + " is missing or is empty or null";
    }

    public static String successOperationMessage(String entityName, String operation) {
        return entityName + " " + operation + " successfully.";
    }
}
