package com.MMOManagement.SpringApp.Controllers.gui;

import com.MMOManagement.SpringApp.Constants.FxmlNames;
import com.MMOManagement.SpringApp.Constants.Messages;
import com.MMOManagement.SpringApp.Model.Users.Moderation.MistrzGry;
import com.MMOManagement.SpringApp.Model.Users.Moderation.ModeratorCzatu;
import com.MMOManagement.SpringApp.Model.Users.Playerbase.Gracz;
import com.MMOManagement.SpringApp.Service.CharacterAccessService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

@Component
public class MainMenuController extends SpringGUIController {


    @Autowired
    CharacterAccessService characterAccessService;

    private String message;
    ObservableList<Gracz> gracze;
    @FXML
    private ListView<Gracz> listaGraczy;
    @FXML
    private Text moderatorInfo;
    @FXML
    private Button powrot;
    @FXML
    private Button characters;
    @FXML
    private Button submit;

    private ModeratorCzatu selectedModerator;

    @FXML
    public void initialize() {
        gracze = FXCollections.observableArrayList(characterAccessService.getPlayers());
        listaGraczy.setItems(gracze);
        listaGraczy.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listaGraczy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean noSelection = newValue == null;
            characters.setDisable(noSelection);
            submit.setDisable(noSelection);
            moderatorInfo.setText(noSelection ? "" : newValue.getModeratorCzatu() == null ? "Brak ostatniego Moderatora" : newValue.getModeratorCzatu().toString());
        });
    }

    public void setSelectedModerator(ModeratorCzatu selectedModerator) {
        this.selectedModerator = selectedModerator;
    }


    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MODERATORLIST));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            ModeratorListController controller = loader.getController();
            controller.setContext(context);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int assignValue(ModeratorCzatu mode) {
      Optional<MistrzGry> master = characterAccessService.getMistrzGry(mode.getId());
        if (master.isEmpty()) {
            return 0;
        }
        MistrzGry mg = master.get();
        switch (mg.getRanga()) {
            case podstawowy:
                return 1;
            case starszy:
                return 2;
            case glowny:
                return 3;
        }
        return 0;
    }
    private boolean checkDate(Gracz gracz) {
        if(gracz.getDataZmianyCzatu()==null)
            return true;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        return gracz.getDataZmianyCzatu().after(calendar.getTime());
    }
    private boolean checkforNull(Gracz gracz, ModeratorCzatu moderatorCzatu) {
        if (gracz == null || moderatorCzatu == null) {
               message = Messages.NULL;
            return true;
        }
        return false;
    }
    private boolean checkMode(Gracz gracz, ModeratorCzatu moderatorCzatu) {
        if (gracz.getModeratorCzatu() == null)
            return false;
        ModeratorCzatu mode = gracz.getModeratorCzatu();
        if (assignValue(moderatorCzatu) >= assignValue(mode))
            return false;

        message = Messages.HIGHRANK;
        return true;
    }
    private boolean isModerator(Gracz gracz) {

            Optional<ModeratorCzatu> moderator = characterAccessService.getModerator(gracz.getId());
            if (moderator.isPresent()) {
                 message = Messages.MODERATOR;
                return true;
        }
        return false;
    }
    private boolean checkRelation(Gracz gracz, ModeratorCzatu moderatorCzatu){
        if(checkforNull(gracz,moderatorCzatu)) return false;
        if(isModerator(gracz)) return false;
        if(checkDate(gracz)){
            if(checkMode(gracz,moderatorCzatu)) return false;
            message=Messages.SUCCESS;
            return true;
        }

return true;
    }

    @FXML
    void changestatus(ActionEvent event) {
        try {
            if (listaGraczy.getSelectionModel().getSelectedItem() != null) {
                if(checkRelation(listaGraczy.getSelectionModel().getSelectedItem(),selectedModerator)){
                    characterAccessService.setRelation(listaGraczy.getSelectionModel().getSelectedItem(),selectedModerator);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MESSAGE));
                loader.setControllerFactory(context::getBean);
                Parent root = loader.load();
                MessageController controller = loader.getController();
                controller.setContext(context);
                controller.setMessage(message);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showCharacters(ActionEvent event) {
      try {
            if (listaGraczy.getSelectionModel().getSelectedItem() != null) {
               if (selectedModerator.getClass() == MistrzGry.class) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.CHARACTERS));
                   loader.setControllerFactory(context::getBean);
                   Parent root = loader.load();
                   CharacterListController controller = loader.getController();
                   controller.setContext(context);
                   controller.setGracz(listaGraczy.getSelectionModel().getSelectedItem());
                   controller.setModerator(selectedModerator);
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(new Scene(root));
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MESSAGE));
                    loader.setControllerFactory(context::getBean);
                    Parent root = loader.load();
                    MessageController controller = loader.getController();
                    controller.setContext(context);
                    controller.setMessage(Messages.MGREQ);
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(new Scene(root));}

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
