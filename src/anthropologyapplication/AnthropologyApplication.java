/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import Logger.FileLogger;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Duke
 */
public class AnthropologyApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       
        GuiHandler myGuiHandler = new GuiHandler(primaryStage, 800, 600); //Loads gui and main menu;
        MainGameCode myCode = new MainGameCode(myGuiHandler); //Starts main menu
        
        
      
        //StackPane root = new StackPane();
        
        //Scene scene = new Scene(root, 300, 250);
        
        //primaryStage.setScene(scene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        FileLogger.flushLogsToFiles();
    }
    
    
}
