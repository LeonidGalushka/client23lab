package sample.view.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import sample.controller.Winner;
import sample.model.JsonEventClient;
import sample.model.enums.GunEvent;
import sample.model.enums.MoveGun;
import sample.model.enums.OtherGameClientMessage;
import sample.model.enums.User;
import sample.model.utils.Constants;
import sample.model.utils.JacksonHelper;

public class SampleController {

    // button
    @FXML
    private Button downButton;
    @FXML
    private Button upButton;
    @FXML
    private Button shotButton;

    // gun
    @FXML
    private Line gunLine;
    @FXML
    private Line gunLine1;

    //bullet
    @FXML
    private Ellipse bullet;
    @FXML
    private Ellipse bullet1;

    @FXML
    private Button exitButton;

    // id
    @FXML
    private TextField idUser;

    //physics
    @FXML
    private TextField V_0TextField;

    private double V_0 = 40.0;
    private final double g = 9.8;

    //sendMsg
    private Controller controller;

    //JsonModel
    private JsonEventClient jsonEventClient;

    @FXML
    private TextField testTestField;

    private void initZeroPosition() {
        bullet.setLayoutX(gunLine.getLayoutX());
        bullet.setLayoutY(gunLine.getLayoutY());
        bullet1.setLayoutX(gunLine1.getLayoutX());
        bullet1.setLayoutY(gunLine1.getLayoutY());
    }

    public void closeWindowEvent() {
        sendMessage("close");
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    void initialize() {
        jsonEventClient = new JsonEventClient();
        controller = Controller.getInstance();
        initZeroPosition();

        exitButton.setOnAction(actionEvent -> closeWindowEvent());

        upButton.setOnAction(actionEvent -> {
            sendMessage(MoveGun.UP.name());
        });

        downButton.setOnAction(actionEvent -> {
            sendMessage(MoveGun.DOWN.name());
        });

        shotButton.setOnAction(actionEvent -> {
            sendMessage(GunEvent.BUH.name());
        });

        (new Thread(() -> {
            String serverMassage;
            while (true) {
                if (!(serverMassage = controller.getMessageClient()).equals(Constants.NUN_MESSAGE)) {
                    event(serverMassage);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }

    public void sendMessage(String messageEvent) {

        jsonEventClient.setId(idUser.getText());
        jsonEventClient.setEvent(messageEvent);
        jsonEventClient.setV_0(String.valueOf(getV_0()));

        messageEvent = JacksonHelper.eventClientToJson(jsonEventClient);

        try {
            controller.setMessageClient(messageEvent);
            Thread.sleep(100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void event(String answerMessage) {

        if (answerMessage.equals("exception_json_format\n")){
            testTestField.setText(String.format("у игрока %s неверный формат json", idUser.getText()));
            return;
        }

        jsonEventClient = JacksonHelper.jsonToEventClient(answerMessage);

        if (jsonEventClient.getEvent().equals("close")){
            testTestField.setText("соперник отключился от игры!");
        }

        if (jsonEventClient.getEvent().equals(MoveGun.UP.name())) {
            moveGun(MoveGun.UP, jsonEventClient.getId());
        }
        if (jsonEventClient.getEvent().equals(MoveGun.DOWN.name())) {
            moveGun(MoveGun.DOWN, jsonEventClient.getId());
        }
        if (jsonEventClient.getEvent().equals(GunEvent.BUH.name())) {
            puhPuh(jsonEventClient);
        }
    }

    private void moveGun(MoveGun moveGun, String idUser) {

        if (moveGun.equals(MoveGun.UP)) {
            if (idUser.equals(User.USER_1.getTitle())) {
                gunLine.setEndX(gunLine.getEndX() - 1);
                gunLine.setEndY(gunLine.getEndY() - 1);
                setBulletXY(gunLine.getEndX(), gunLine.getEndY(), idUser);
            }
            if (idUser.equals(User.USER_2.getTitle())) {
                gunLine1.setEndX(gunLine1.getEndX() - 1);
                gunLine1.setEndY(gunLine1.getEndY() - 1);
                setBulletXY(gunLine1.getEndX(), gunLine1.getEndY(), idUser);
            }
        }

        if (moveGun.equals(MoveGun.DOWN)) {
            if (idUser.equals(User.USER_1.getTitle())) {
                gunLine.setEndX(gunLine.getEndX() + 1);
                gunLine.setEndY(gunLine.getEndY() + 1);
                setBulletXY(gunLine.getEndX(), gunLine.getEndY(), idUser);
            }
            if (idUser.equals(User.USER_2.getTitle())) {
                gunLine1.setEndX(gunLine1.getEndX() + 1);
                gunLine1.setEndY(gunLine1.getEndY() + 1);
                setBulletXY(gunLine1.getEndX(), gunLine1.getEndY(), idUser);
            }
        }
    }

    private void setBulletXY(double x, double y, String idUser) {
        testTestField.setText("");
        if (idUser.equals(User.USER_1.getTitle())) {
            bullet.setCenterX(x);
            bullet.setCenterY(y);
        }

        if (idUser.equals(User.USER_2.getTitle())) {
            bullet1.setCenterX(x);
            bullet1.setCenterY(y);
        }
    }

    private void puhPuh(JsonEventClient jsonEventClient) {
        V_0 = Double.parseDouble(jsonEventClient.getV_0());
        String idUser = jsonEventClient.getId();
        if (idUser.equals(User.USER_1.getTitle())) {
            setBulletXY(bullet.getCenterX() + getSx(getT(getSinAlfa(User.USER_1), V_0), V_0, User.USER_1),
                    gunLine.getStartY(),
                    idUser
            );

            if (Winner.isWinner(bullet.getCenterX(), testTestField, idUser)) {
                sendMessage(OtherGameClientMessage.WINNER.name());
            }
        }

        if (idUser.equals(User.USER_2.getTitle())) {
            setBulletXY(bullet1.getCenterX() + getSx(getT(getSinAlfa(User.USER_2), V_0), -V_0, User.USER_2),
                    gunLine1.getStartY(),
                    idUser
            );

            if (Winner.isWinner(bullet1.getCenterX(), testTestField, idUser)) {
                sendMessage(OtherGameClientMessage.WINNER.name());
            }
        }
    }

    // physics --------------------------------------------------------------------------------------
    private double getV_0() {
        try {
            double V_0 = Double.parseDouble(V_0TextField.getText());
            if (V_0 <= 0) throw new Exception();
            return V_0;
        } catch (Exception ex) {
            return 40.0;
        }
    }

    private double getSx(double T, double V_0, User user) {
        return V_0 * T * (1 - Math.pow(getSinAlfa(user), 2));
    }

    private double getT(double sinAlfa, double V_0) {
        return 2 * V_0 * sinAlfa / g;
    }

    private double getSinAlfa(User user) {
        double result = 0.0;
        if (user.equals(User.USER_1)) {
            result = getOppositeСathet(User.USER_1) / getHypotenuse(User.USER_1);
        }
        if (user.equals(User.USER_2)) {
            result = getOppositeСathet(User.USER_2) / getHypotenuse(User.USER_2);
        }
        return result;
    }

    private double getOppositeСathet(User user) {
        double result = 0.0;
        if (user.equals(User.USER_1)) {
            result = Math.sqrt(Math.pow((gunLine.getEndY() - gunLine.getStartY()), 2));
        }
        if (user.equals(User.USER_2)) {
            result = Math.sqrt(Math.pow((gunLine1.getEndY() - gunLine1.getStartY()), 2));
        }
        return result;
    }

    private double getHypotenuse(User user) {
        double result = 0.0;
        if (user.equals(User.USER_1)) {
            result = Math.sqrt(
                    Math.pow((gunLine.getEndX() - gunLine.getStartX()), 2) +
                            Math.pow((gunLine.getStartY() - gunLine.getEndY()), 2)
            );
        }
        if (user.equals(User.USER_2)) {
            result = Math.sqrt(
                    Math.pow((gunLine1.getEndX() - gunLine1.getStartX()), 2) +
                            Math.pow((gunLine1.getStartY() - gunLine1.getEndY()), 2)
            );
        }
        return result;
    }

}
