package com.MMOManagement.SpringApp.Controllers.gui.TODO;

import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import javafx.event.ActionEvent;
import com.MMOManagement.SpringApp.Constants.FxmlNames;
//import LegacyFiles.DatabaseManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.io.IOException;

public class CharacterListController {

    @FXML
    private ListView<Postac> listaChar;

    private ObservableList<Postac> postaci;
    @FXML
    public void initialize() {
       // postaci = FXCollections.observableArrayList(DatabaseManager.getInstance().getGracz().getPostaci());
        listaChar.setItems(postaci);
        listaChar.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void deleteCharacter(ActionEvent event) {

        Postac selected = listaChar.getSelectionModel().getSelectedItem();
     //  DatabaseManager.getInstance().deleteCharacter(selected);
       postaci.remove(listaChar.getSelectionModel().getSelectedItem());
        listaChar.setItems(postaci);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MESSAGE));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(loader.load()));
        }catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MAINMENU));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
