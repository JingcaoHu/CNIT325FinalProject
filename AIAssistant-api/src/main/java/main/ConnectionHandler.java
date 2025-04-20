package main;

public interface ConnectionHandler {
    String runConnection(int port, String address, Prompt passedInfo);
}
