/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;

/**
 *
 * @author noone
 */
public class BuildingProgressBar extends javafx.scene.control.ProgressBar {
    private javafx.scene.control.Label;
    private final BuildingConstructionDisplayData BuildingData;
    public BuildingProgressBar(BuildingConstructionDisplayData myData)
    {
        this.BuildingData = myData;
        //Programmatically create Label over the bar
    }
    
}
