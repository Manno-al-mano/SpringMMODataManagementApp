package com.MMOManagement.SpringApp.Controllers.gui;

import com.MMOManagement.SpringApp.Constants.FxmlNames;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class MessageController extends SpringGUIController {

    @FXML
    private Button zamknij;

    @FXML
    private Label message;

    @FXML
    public void initialize() {
    //    message.setText(DatabaseManager.getInstance().getMessage());

    }

    public void setMessage(String message) {
    this.message.setText(message);
    }

    @FXML
    void back(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MODERATORLIST));
        loader.setControllerFactory(context::getBean);
            Parent root=loader.load();
            ModeratorListController controller = loader.getController();
            controller.setContext(context);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


