package me.empire.commandapi.exception;

public class CommandExitException extends Exception {
    public CommandExitException(String message) {
        super(message);
    }
}