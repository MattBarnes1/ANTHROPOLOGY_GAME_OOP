/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.MainGameCode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Duke
 */
public class CreatingWorldScreenController implements Initializable {

    @FXML
    private ProgressBar creatingWorldMapProgressBar;
    @FXML
    private Label LoadScreenLabel;
    @FXML
    private TextArea MapText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    MainGameCode myMain;
    
    public void setMainGameCode(MainGameCode aThis) {
    myTimer =  new AnimationTimer()
    {
        AnimationTimer InternalControl = myTimer;
        @Override
        public void handle(long now) {
            try {
                //System.out.println(myMain.getProgress());
                if(myMain.getProgress() == -1)
                {
                    myMain.finishMapSetup();
                    if(InternalControl != null)  InternalControl.stop();
                }
                creatingWorldMapProgressBar.setProgress(myMain.getProgress());
                LoadScreenLabel.setText(myMain.getMessage());
            } catch (Throwable ex) {
                Logger.getLogger(CreatingWorldScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }



    };
        myMain = aThis;
    }
    AnimationTimer myTimer;
    public void startUpdatingScreenData() {
        if(myTimer != null) myTimer.start();
    }
    public void stopUpdatingScreenData() {
        if(myTimer != null) myTimer.stop();
    }
}
