/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2ui;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 *
 * @author nunop
 */
public class Alerta extends Application {

public static void main(String[] args) {
    launch(args);

}

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
public void start(Stage primaryStage) throws Exception {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Erro!");
    alert.setHeaderText(null);
    alert.setContentText("O programa já está em execução");
    alert.showAndWait();
    }

    public void start(JFXPanel fxPanel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}