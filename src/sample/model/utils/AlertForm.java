package sample.model.utils;

import javafx.scene.control.Alert;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AlertForm {

    public void errorAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Победа!!!");
        alert.setHeaderText(info);
        alert.showAndWait();
    }

}
