package com.MMOManagement.SpringApp.Controllers.gui;

import com.MMOManagement.SpringApp.Constants.Messages;
import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import com.MMOManagement.SpringApp.Model.Users.Moderation.ModeratorCzatu;
import com.MMOManagement.SpringApp.Model.Users.Playerbase.Gracz;
import com.MMOManagement.SpringApp.Service.CharacterAccessService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import com.MMOManagement.SpringApp.Constants.FxmlNames;
//import LegacyFiles.DatabaseManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CharacterListController extends SpringGUIController {
    ModeratorCzatu selectedModerator;

    @Autowired
    CharacterAccessService characterAccessService;
    @FXML
    private ListView<Postac> listaChar;

    private ObservableList<Postac> postaci;
    @FXML
    public void initialize() {

    }

    public void deleteCharacter(ActionEvent event) {

        Postac selected = listaChar.getSelectionModel().getSelectedItem();
       postaci.remove(listaChar.getSelectionModel().getSelectedItem());
        listaChar.setItems(postaci);
        characterAccessService.deleteCharacter(selected);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MESSAGE));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            MessageController controller = loader.getController();
            controller.setContext(context);
            controller.setMessage(Messages.DELETED);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MAINMENU));
            loader.setControllerFactory(context::getBean);
            Parent root= loader.load();
            MainMenuController controller = loader.getController();
            controller.setContext(context);
            controller.setSelectedModerator(selectedModerator);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGracz(Gracz selectedItem) {
        postaci = FXCollections.observableArrayList(selectedItem.getPostaci());
        listaChar.setItems(postaci);
        listaChar.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void setModerator(ModeratorCzatu selectedModerator) {
        this.selectedModerator=selectedModerator;
    }
}
