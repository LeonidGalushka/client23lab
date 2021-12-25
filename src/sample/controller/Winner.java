package sample.controller;

import javafx.scene.control.TextField;
import lombok.experimental.UtilityClass;



@UtilityClass
public class Winner {

    public boolean isWinner(Double xBulletExit, TextField textField, String idUser) {
        if (Math.abs(-406 - xBulletExit) < 30 || Math.abs(420 - xBulletExit) < 30) {
            //textField.setText(xBulletExit.toString());
            textField.setText(textWinner(idUser));
            return true;
        }
        return false;
    }

    private String textWinner(String idUser) {
        return String.format(String.format("выиграл %s игрок", idUser));
    }

}
