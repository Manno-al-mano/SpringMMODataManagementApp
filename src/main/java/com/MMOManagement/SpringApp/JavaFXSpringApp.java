package com.MMOManagement.SpringApp;

import com.MMOManagement.SpringApp.Constants.FxmlNames;
import com.MMOManagement.SpringApp.Controllers.gui.ModeratorListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavaFXSpringApp extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(JavaFXSpringApp.class);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlNames.MODERATORLIST));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        ModeratorListController controller = loader.getController();
        controller.setContext(context);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("My Application");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        context.stop();
        super.stop();
        System.out.println("Zamykamy :)");
    }

    public static void start(String[] args) {
        launch(args);
    }



}
