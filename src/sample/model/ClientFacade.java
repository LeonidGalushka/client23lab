package sample.model;

import sample.model.utils.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFacade {

    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientFacade() throws IOException {
        clientSocket = new Socket(Constants.IP, Constants.PORT);
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void setMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    public String getMessage() throws IOException {
        String serverMessage = Constants.NUN_MESSAGE;
        while (in.available() > 0) {
            serverMessage += in.readUTF() + '\n';
        }
        return serverMessage;
    }

    public void closeIO() throws IOException {
        clientSocket.close();
        in.close();
        out.close();
    }
}
