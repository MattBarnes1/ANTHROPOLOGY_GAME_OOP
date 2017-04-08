/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;

/**
 *
 * @author Duke
 */
public class QueueButton extends javafx.scene.control.Button  {
    BuildingConstructionDisplayData myConstruction;
    QueueButton(BuildingConstructionDisplayData myConstruction) {
        super(myConstruction.getName());
        this.myConstruction = myConstruction;
    }
    
}
