package sample.model;

import java.io.IOException;

public class Client {
    ClientFacade clientFacade;

    public Client() throws IOException {
        clientFacade = new ClientFacade();
    }

    public void setMessage(String message) throws IOException {
        clientFacade.setMessage(message);
    }

    public String getMessage() throws IOException {
        return clientFacade.getMessage();
    }

    public void close() throws IOException {
        clientFacade.closeIO();
    }
}
