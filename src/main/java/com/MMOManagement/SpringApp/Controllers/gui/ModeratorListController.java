package com.MMOManagement.SpringApp.Controllers.gui;

import com.MMOManagement.SpringApp.Constants.FxmlNames;
import com.MMOManagement.SpringApp.Model.Users.Moderation.ModeratorCzatu;
//import MMOdata.DatabaseManager;
import com.MMOManagement.SpringApp.Service.CharacterAccessService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ModeratorListController {

    @Autowired
    CharacterAccessService characterAccessService;

    @FXML
    private Button exitButton;

    @FXML
    private ListView<ModeratorCzatu> listaMod;
    private ObservableList<ModeratorCzatu> mods;

    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {
        mods = FXCollections.observableArrayList(characterAccessService.getModerators());
        listaMod.setItems(mods);
        listaMod.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        System.out.println(listaMod);
    }

    @FXML
    void changestatus(ActionEvent event) {
        try {
            if(listaMod.getSelectionModel().getSelectedItem()!=null) {
                ModeratorCzatu selectedModerator = listaMod.getSelectionModel().getSelectedItem();//DatabaseManager.getInstance().setModeratorCzatu(listaMod.getSelectionModel().getSelectedItem());
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MAINMENU));
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(loader.load()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exit(ActionEvent event) {
    System.exit(0);
    }

}
