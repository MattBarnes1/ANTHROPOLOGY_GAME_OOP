/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.DisplayData.DisplayData;
import anthropologyapplication.MainGameCode;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author mpb38
 */
public class CustomHBox extends HBox {
        Label myLabel = new Label();
        customProgressBar myBar;
        public CustomHBox(DisplayData myData, MainGameCode myCode)
        {
            myBar = new customProgressBar(myData, myCode);
            myLabel.setText(myData.getName());
            this.getChildren().add(myLabel);
            this.getChildren().add(myBar);
        }
    
        public void update()
        {
            myBar.update();
        }
        
        public boolean isDone()
        {
            return myBar.isDone();
        }
    
}
