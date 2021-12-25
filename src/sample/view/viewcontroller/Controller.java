package sample.view.viewcontroller;

import sample.model.Client;

import java.io.IOException;

public class Controller {

    public static Controller instance;
    private Client client;
    private String messageClient;

    private Controller() throws IOException {
        client = new Client();
    }

    public static Controller getInstance() {
        if (instance == null) {
            try {
                instance = new Controller();
            } catch (IOException e) {
            }
        }
        return instance;
    }

    public void setMessageClient(String messageClient) {
        this.messageClient = messageClient;
        try {
            client.setMessage(messageClient);
        } catch (IOException e) {
            //AlertForm.errorAlert(Constants.ERROR_SET_MESSAGE);
        }
    }

    public String getMessageClient() {
        try {
            return client.getMessage();
        } catch (IOException e) {
            //AlertForm.errorAlert(Constants.ERROR_GET_MESSAGE);
        }
        return null;
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
           // AlertForm.errorAlert(Constants.ERROR_CLOSE_MESSAGE);
        }
    }
}
